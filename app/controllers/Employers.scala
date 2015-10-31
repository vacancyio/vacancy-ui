package controllers

import model.EmployerPartial
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import repository.EmployerRepository

class Employers extends Controller {

  private val employerRegistrationForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(EmployerPartial.apply)(EmployerPartial.unapply)
  )

  def index = Action {
    val employers = EmployerRepository.all()
    Ok(views.html.employers.index(employers))
  }

  def show(name: String) = Action {
    NotImplemented
  }

  def register = Action { implicit request =>
    Ok(views.html.employers.register(employerRegistrationForm))
  }

  def create = Action { implicit request =>
    employerRegistrationForm.bindFromRequest.fold(
      formWithErrors => {
        Ok(views.html.employers.register(formWithErrors)).flashing("error" -> "Form contains errors")
      },
      employerData => {
        Ok
      }
    )
  }
}
