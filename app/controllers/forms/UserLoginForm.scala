package controllers.forms

import model.LoginData
import play.api.data.Form
import play.api.data.Forms._

trait UserLoginForm {

  val loginForm = Form(
    mapping("email" -> email, "password" -> nonEmptyText(minLength = 6))
    (LoginData.apply)(LoginData.unapply)
  )
}
