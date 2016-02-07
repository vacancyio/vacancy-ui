package controllers

import controllers.forms.UserLoginForm
import model.User
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import repository.UserRepository

class UserProfiles extends Controller with UserLoginForm {

  def show = Action { implicit request =>
    getSessionUser.fold(BadRequest(views.html.users.login(loginForm))) { user =>
      Ok(views.html.users.show(user))
    }
  }

  def edit = Action { implicit request =>
    getSessionUser.fold(BadRequest(views.html.users.login(loginForm))) { user =>
      Ok(views.html.users.edit(user))
    }
  }

  private def getSessionUser(implicit request: Request[AnyContent]): Option[User] =
    request.session.get("uid") flatMap { uid => UserRepository.findOneByID(uid.toLong) }
}
