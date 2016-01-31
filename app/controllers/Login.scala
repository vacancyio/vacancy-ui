package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import model.LoginData

class Login extends Controller {

  private val loginForm = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText)
    (LoginData.apply)(LoginData.unapply))

  def userLogin = Action { implicit request =>
    Ok(views.html.users.login(loginForm))
  }
}