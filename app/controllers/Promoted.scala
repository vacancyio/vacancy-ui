package controllers

// Promoted jobs (editor picks of the most interesting roles)

import play.api.mvc._

class Promoted extends Controller {

  def index() = Action {
    Ok(views.html.promoted.index())
  }
}