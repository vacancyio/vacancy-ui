package controllers

import model._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repository.JobRepository
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class Jobs extends Controller  {

  private val form = Form(
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "employer" -> nonEmptyText,
      "location" -> nonEmptyText,
      "application" -> optional(text),
      "salary" -> optional(text),
      "remote" -> boolean,
      "contract" -> boolean
    )(JobPartial.apply)(JobPartial.unapply)
  )

  def index(page: Option[Int] = None, query: Option[String] = None) = Action {

    val pagesTotal = JobRepository.totalJobs / JobRepository.jobsPerPage

    val jobs = query match {
      case Some(q) => JobRepository.search(q)
      case None    => JobRepository.all(page.getOrElse(1))
    }

    Ok(views.html.jobs.index(pagesTotal, jobs))
  }

  def show(slug: String) = Action {
    val id = slug.split("-").headOption map (_.toLong) getOrElse 1L
    JobRepository.findOneById(id) map { job =>
      Ok(views.html.jobs.show(job))
    } getOrElse NotFound
  }

  def add = Action { implicit request =>
    Ok(views.html.jobs.add(form))
  }

  def create = Action { implicit request =>
    form.bindFromRequest.fold(
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
