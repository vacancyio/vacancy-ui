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
    )(EmployerPartial.apply)(EmployerPartial.unapply) verifying("Email already exists", fields => fields match {
      case data => EmployerRepository.findOneByEmail(data.email).isEmpty }))

  def index = Action { implicit request =>
    val employers = EmployerRepository.all()
    Ok(views.html.employers.index(employers))
  }

  def show(id: Long) = Action { implicit request =>
    EmployerRepository.findOneById(id) map { employer =>
      Ok(views.html.employers.show(employer))
    } getOrElse NotFound
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
        EmployerRepository.insert(employerData)
        Redirect(routes.Dashboard.employers())
          .withSession("email" -> employerData.email)
      }
    )
  }
}
