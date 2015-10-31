package controllers

import play.api.mvc._
import repository.JobRepository
import security.SecuredAction

class Dashboard extends Controller with SecuredAction {

  def employers = withEmployer { employer => { implicit request =>

    val jobs = employer.id map { id => JobRepository.forEmployer(id) } getOrElse Nil
    Ok(views.html.employers.dashboards.index(employer, jobs))
  }}
}
