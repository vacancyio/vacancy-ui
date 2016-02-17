package controllers

import play.api.cache.Cached
import play.api.mvc._

class Static extends Controller {

  import play.api.Play.current

  def index = Cached("static.index") {
    Action { implicit request =>
      Ok(views.html.static.index())
    }
  }

  def api = Cached("static.api"){
    Action { implicit request =>
      Ok(views.html.static.api())
    }
  }
}
