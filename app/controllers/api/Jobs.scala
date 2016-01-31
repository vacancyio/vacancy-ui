package controllers.api

import controllers.api.security.APISecuredAction
import model._
import play.api.libs.json._
import play.api.mvc._
import repository.JobRepository

class Jobs extends Controller {

  def index(page: Int = 1) = Action {
    Ok(Json.toJson(JobRepository.all(page)))
  }

  def show(id: Long) = Action {
    JobRepository.findOneById(id) map { job =>
      Ok(Json.toJson(job))
    } getOrElse NotFound
  }

  def update(id: Long) = APISecuredAction(parse.json) { request =>
    if (JobRepository.findOneById(id).isDefined) {
      request.body.validate[JobPartial].map { case partial =>
        val job = Job.fromPartial(partial).copy(id = Some(id))
        JobRepository.update(job)
        Ok(Json.toJson(job))
      }.recoverTotal { e => BadRequest(JsError.toJson(e.errors)) }
    } else NotFound
  }

  def create = APISecuredAction(parse.json) { request =>
    request.body.validate[JobPartial].map { case partial =>
      val job = Job.fromPartial(partial)
      JobRepository.insert(job)
      Created(Json.toJson(job))
    }.recoverTotal{
      e => BadRequest(JsError.toJson(e.errors))
    }
  }

  def delete(id: Long) = APISecuredAction {
    if (JobRepository.findOneById(id).isDefined) {
      JobRepository.delete(id)
      Ok
    }
    else NotFound
  }
}
