package controllers

import play.api.cache.Cached
import play.api.mvc._

class Static extends Controller {

  def index =
    Cached("static-index") {
      Action { implicit request =>
        Ok(views.html.index())
      }
    }
}
