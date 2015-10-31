package controllers

import play.api.mvc._
import security.SecuredAction

class EmployerDashboard extends Controller with SecuredAction {

  def index = withEmployer { employer => { implicit request =>
    Ok(views.html.employers.dashboards.index(employer))
  }}
}
