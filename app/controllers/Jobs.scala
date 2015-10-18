package controllers

import forms._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._

class Jobs extends Controller {

  val form = Form(
    mapping(
      "title"   -> text,
      "jobType" -> text
    )(StandardJob.apply)(StandardJob.unapply)
  )

  def index = Action {
    Ok(views.html.jobs.index())
  }

  def show(id: String) = Action {
    Ok(views.html.jobs.show(id))
  }

  def selection = Action {
    Ok(views.html.jobs.selection())
  }

  def standard = Action {
    Ok(views.html.jobs.standard(form))
  }

  def promoted = Action {
    Ok(views.html.jobs.promoted())
  }

  def create = Action { request =>
    println(request)
    Ok(request.toString())
  }
}
