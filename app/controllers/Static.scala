package controllers

import play.api.mvc._

class Static extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }
}
