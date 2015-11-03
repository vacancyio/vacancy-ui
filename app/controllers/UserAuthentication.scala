package controllers

import model.LoginData
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

class UserAuthentication extends Controller {

  private val loginForm = Form(
    mapping("email" -> email, "password" -> nonEmptyText)(LoginData.apply)(LoginData.unapply))

  def login = Action { implicit request =>
    Ok(views.html.users.login())
  }
}
