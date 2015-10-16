package controllers

import play.api.mvc._

class Static extends Controller {

  def index = Action {
    Ok(views.html.index())
  }
}
