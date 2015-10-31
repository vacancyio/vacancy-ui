package controllers.api

import play.api.mvc._
import repository.JobRepository
import play.api.libs.json._

class JobsApi extends Controller {

  def index = Action {
    val jobs = JobRepository.all()
    Ok(Json.toJson(jobs))
  }

  def show(id: Long) = Action {
    JobRepository.findOneById(id) map { job =>
      Ok(Json.toJson(job))
    } getOrElse NotFound.as("application/json")
  }

  def create = Action {
    NotImplemented
  }

  def update(id: Long) = Action {
    NotImplemented
  }

  def delete(id: Long) = Action {
    NotImplemented
  }
}
