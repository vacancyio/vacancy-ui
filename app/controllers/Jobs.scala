package controllers

import model.{Job, JobPartial}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repository.JobRepository
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import security.EmployerSecuredAction

class Jobs extends Controller with EmployerSecuredAction {

  private val jobForm = Form(
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "skills" -> optional(text),
      "apply" -> optional(text),
      "contract" -> number,
      "remote" -> boolean,
      "city" -> optional(text),
      "country" -> text(minLength = 2)
    )(JobPartial.apply)(JobPartial.unapply)
  )

  def index = Action { implicit request =>
    val jobs = JobRepository.all()
    Ok(views.html.jobs.index(jobs))
  }

  def show(id: Long) = Action { implicit request =>
    JobRepository.findOneById(id) map { job =>
      Ok(views.html.jobs.show(job))
    } getOrElse NotFound
  }

  def edit(id: Long) = withEmployer { employer => { implicit request =>
    val maybeJob = employer.id flatMap { employerID => JobRepository.findOneByIdForEmployer(employerID, id) }
    maybeJob map { job =>
      val partial = JobPartial(job.title, job.description, job.skills, job.application, job.contract, job.remote, job.city, job.country)
      Ok(views.html.jobs.edit(job, jobForm.fill(partial)))
    } getOrElse NotFound
  }}

  def update(id: Long) = withEmployer { employer => { implicit request =>
    jobForm.bindFromRequest.fold(
      formWithErrors => {
        JobRepository.findOneById(id) map { job =>
          Ok(views.html.jobs.edit(job, formWithErrors))
        } getOrElse NotFound
      },
      jobPartial => {
        JobRepository.update(employer, jobPartial)
        Redirect(routes.Jobs.show(id))
          .flashing("success" -> "Job updated successfully")
      }
    )
  }}

  def selection = Action { implicit request =>
    Ok(views.html.jobs.selection())
  }

  def promoted = Action { NotImplemented }

  def standard = withEmployer { employer => { implicit request =>
    Ok(views.html.jobs.standard(jobForm))
  }}

  def create = withEmployer { employer => { implicit request =>
    jobForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.jobs.standard(formWithErrors))
          .flashing("error" -> "Form contains errors")
      },
      jobPartial => {
        JobRepository.insert(employer, jobPartial) // TODO check for errors!
        Redirect(routes.Jobs.index())
          .flashing("success" -> "Job added successfully")
      }
    )
  }}
}
