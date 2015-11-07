package controllers.api

import controllers.api.security.APISecuredAction
import model.JobPartial
import play.api.mvc._
import repository.JobRepository
import play.api.libs.json._

class Jobs extends Controller {

  /**
   * Return all jobs in the system. This isn't specific to employers? Maybe it should be
   *
   * @return
   */
  def index = APISecuredAction { request =>
    Ok(Json.toJson(JobRepository.all()))
  }

  def show(id: Long) = APISecuredAction { request =>
    JobRepository.findOneById(id) map { job =>
      Ok(Json.toJson(job))
    } getOrElse NotFound.as("application/json")
  }

  def create = APISecuredAction { request =>
    request.body.asJson.map { body =>
      body.asOpt[JobPartial].map { partial =>
        JobRepository.insert(request.employer, partial)
        Created("Ok").as("application/json")
      } getOrElse BadRequest("Invalid payload").as("application/json")
    } getOrElse BadRequest
  }

  def update(id: Long) = Action {
    NotImplemented
  }

  def delete(id: Long) = Action {
    NotImplemented
  }
}
