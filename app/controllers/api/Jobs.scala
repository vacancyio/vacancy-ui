package controllers.api

import controllers.api.security.APISecuredAction
import model.JobPartial
import play.api.mvc._
import repository.JobRepository
import play.api.libs.json._

class Jobs extends Controller {

  /**
   * Return all jobs in the system.
   *
   * PUBLIC
   */
  def index(page: Int = 1) = Action {
    Ok(Json.toJson(JobRepository.all(page)))
  }

  /**
   * Show a single job
   *
   * PUBLIC
   *
   * @param id The primary key ID of the job
   */
  def show(id: Long) = Action {
    JobRepository.findOneById(id) map { job =>
      Ok(Json.toJson(job))
    } getOrElse NotFound.as("application/json")
  }

  /**
   * Create a new job. Requires an employer to be registered
   *
   * PRIVATE | EMPLOYERS ONLY
   */
  def create = APISecuredAction { request =>
    request.body.asJson.map { body =>
      body.asOpt[JobPartial].map { partial =>
        JobRepository.insert(request.employer, partial)
        Created("Ok").as("application/json")
      } getOrElse BadRequest("Invalid payload").as("application/json")
    } getOrElse BadRequest
  }

  /**
   * Update an existing job. This action can only be performed by employers for jobs they own
   *
   * PRIVATE | EMPLOYERS ONLY
   */
  def update(id: Long) = Action {
    NotImplemented
  }

  /**
   * Delete a job. This action can only be performed by employers for jobs they own
   *
   * PRIVATE | EMPLOYERS ONLY
   */
  def delete(id: Long) = Action {
    NotImplemented
  }
}
