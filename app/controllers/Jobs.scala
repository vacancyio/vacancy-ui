package controllers

import model.JobPartial
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import repository.JobRepository
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class Jobs extends Controller {

  private val jobForm = Form(
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "skills" -> text,
      "contract" -> number,
      "remote" -> boolean,
      "companyName" -> optional(text),
      "companyWebsite" -> optional(text),
      "city" -> optional(text),
      "country" -> text(minLength = 2)
    )(JobPartial.apply)(JobPartial.unapply)
  )

  def index = Action {
    val jobs = JobRepository.all()
    Ok(views.html.jobs.index(jobs))
  }

  def show(id: String) = Action {
    Ok(views.html.jobs.show(id))
  }

  def selection = Action {
    Ok(views.html.jobs.selection())
  }

  def standard = Action {
    Ok(views.html.jobs.standard(jobForm))
  }

  def promoted = Action {
    NotImplemented
  }

  def create = Action { implicit request =>
    println(request.body)
    jobForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.jobs.standard(formWithErrors))
          .flashing("error" -> "Form contains errors")
      },
      jobPartial => {
        JobRepository.insert(jobPartial) // TODO check for errors!
        Redirect(routes.Jobs.index())
          .flashing("success" -> "Job added successfully")
      }
    )
  }
}
