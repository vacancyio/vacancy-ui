package controllers

import model._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class EmployerAuthenticationController extends Controller {

  private val loginForm = Form(
    mapping(
      "email"    -> email,
      "password" -> nonEmptyText
    )(EmployerLoginData.apply)(EmployerLoginData.unapply))

  def login = Action { implicit request =>
    Ok(views.html.employers.login(loginForm))
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.employers.login(formWithErrors))
      },
      employer => {
       if (Employer.authenticate(employer.email, employer.password))
         Redirect(routes.Jobs.standard()).withSession("email" -> employer.email)
       else BadRequest(views.html.employers.login(loginForm))
      })
  }

  def logout = Action {
    Redirect(routes.EmployerAuthenticationController.login()).withNewSession.flashing(
      "success" -> "You have been successfully logged out"
    )
  }
}
