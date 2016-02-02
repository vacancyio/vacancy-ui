package controllers

import model.{LoginData, User, UserPartial}
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import repository.UserRepository
import play.api.cache.Cache

class Users extends Controller {

  private def clearCache() =
    Cache.remove("static.index")

  private val userRegistrationForm = Form(
    mapping("email" -> email, "password" -> nonEmptyText)(UserPartial.apply)(UserPartial.unapply)
      verifying("Email or company name already exists", (fields) => fields match { case data =>
        UserRepository.findOneByEmail(data.email).isEmpty
      }))

  private val loginForm = Form(
    mapping("email" -> email, "password" -> nonEmptyText(minLength = 6))
    (LoginData.apply)(LoginData.unapply)
  )

  def register = Action { implicit request =>
    Ok(views.html.users.register(userRegistrationForm))
  }

  def show(id: Long) = Action { implicit request =>
    UserRepository.findOneByID(id) match {
      case Some(user) => Ok(views.html.users.show(user))
      case None => NotFound
    }
  }

  def create = Action { implicit request =>
    userRegistrationForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.users.register(formWithErrors))
      },
      userPartial => {
        val user = User.fromPartial(userPartial)
        UserRepository.create(user)
        Redirect(routes.Static.index()).withSession("email" -> user.email)
      }
    )
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.users.login(formWithErrors))
      },
      user => {
        if (User.authenticate(user.email, user.password)) {
          clearCache()
          Redirect(routes.Static.index()).withSession("email" -> user.email)
        }
        else BadRequest(views.html.users.login(loginForm))
      })
  }

  def login = Action { implicit request =>
    Ok(views.html.users.login(loginForm))
  }

  def logout = Action {
    Redirect(routes.Users.login()).withNewSession.flashing(
      "success" -> "You have been logged out"
    )
  }
}
