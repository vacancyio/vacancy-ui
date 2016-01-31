package controllers

import model.{User, UserPartial}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class Users extends Controller {

  private val userRegistrationForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText)
    (UserPartial.apply)(UserPartial.unapply))

  def registerUser = Action { implicit request =>
    Ok(views.html.users.register(userRegistrationForm))
  }

  def create = Action { implicit request =>
    userRegistrationForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.users.register(formWithErrors))
      },
      userPartial => {
        Ok(Json.toJson(User.fromPartial(userPartial)))
      }
    )
  }
}
