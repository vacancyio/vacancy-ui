package controllers

import play.api.mvc._

class Jobs extends Controller {

  def index = Action {
    Ok(views.html.jobs.index())
  }

  def show(id: String) = Action {
    Ok(views.html.jobs.show(id))
  }

  def create = Action {
    Ok(views.html.jobs.create())
  }
}
