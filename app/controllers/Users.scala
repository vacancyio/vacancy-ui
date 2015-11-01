package controllers

import play.api.mvc._

class Users extends Controller {

  def index = Action { implicit request =>
    NotImplemented
  }

  def show(username: String) = Action { implicit request =>
    Ok(views.html.users.show())
  }

  def create = Action { implicit request =>
    NotImplemented
  }

  def delete(id: Long) = Action { implicit request =>
    NotImplemented
  }
}
