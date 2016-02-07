package controllers

import controllers.forms.JobForm
import model._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.mvc._
import repository.JobRepository

class Jobs extends Controller with JobForm {

  def index(page: Option[Int] = None, query: Option[String] = None) = Action { implicit request =>

    val pagesTotal = JobRepository.totalJobs / JobRepository.jobsPerPage

    val jobs = query match {
      case Some(q) => JobRepository.search(q)
      case None    => JobRepository.all(page.getOrElse(1))
    }

    Ok(views.html.jobs.index(pagesTotal, jobs))
  }

  def show(slug: String) = Action { implicit request =>
    val id = slug.split("-").headOption map (_.toLong) getOrElse 1L
    JobRepository.findOneById(id) map { job =>
      Ok(views.html.jobs.show(job))
    } getOrElse NotFound
  }

  def add = Action { implicit request =>
    Ok(views.html.jobs.add(jobForm))
  }

  def create = Action { implicit request =>
    jobForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.jobs.add(formWithErrors)).flashing("error" -> "Form contains errors")
      },
      jobPartial => {
        JobRepository.insert(Job.fromPartial(jobPartial))
        Redirect(routes.Jobs.index()).flashing("success" -> "Job added successfully")
      }
    )
  }
}
