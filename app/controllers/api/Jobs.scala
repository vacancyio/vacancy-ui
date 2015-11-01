package controllers.api

import model.JobPartial
import play.api.mvc._
import repository.JobRepository
import play.api.libs.json._
import security.EmployerSecuredAction

class Jobs extends Controller with EmployerSecuredAction {

  def index = Action {
    val jobs = JobRepository.all(50)
    Ok(Json.toJson(jobs))
  }

  def show(id: Long) = Action {
    JobRepository.findOneById(id) map { job =>
      Ok(Json.toJson(job))
    } getOrElse NotFound.as("application/json")
  }

  def create = BasicAuthAction { implicit request =>
    request.body.asJson.map { body => body.asOpt[JobPartial].map { partial =>
      Created("Ok").as("application/json")
    } getOrElse
      BadRequest("Invalid payload").as("application/json")
    } getOrElse BadRequest
  }

  def update(id: Long) = Action {
    NotImplemented
  }

  def delete(id: Long) = Action {
    NotImplemented
  }
}
