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
    )(EmployerPartial.apply)(EmployerPartial.unapply) verifying("Email or company name already exists", fields => fields match { case data =>
      EmployerRepository.findOneByEmail(data.email).isEmpty &&
        EmployerRepository.findOneByName(data.name).isEmpty
    }))

  def index = Action { implicit request =>
    val employers = EmployerRepository.all()
    Ok(views.html.employers.index(employers))
  }

  def show(name: String) = Action { implicit request =>
    EmployerRepository.findOneByName(name) map { employer =>
      Ok(views.html.employers.show(employer))
    } getOrElse NotFound
  }

  def register = Action { implicit request =>
    Ok(views.html.employers.register(employerRegistrationForm))
  }

  def create = Action { implicit request =>
    employerRegistrationForm.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors.errors)
        Ok(views.html.employers.register(formWithErrors))
      },
      employerData => {
        EmployerRepository.insert(employerData)
        Redirect(routes.Dashboard.employers())
          .withSession("email" -> employerData.email)
      }
    )
  }
}
