package controllers.api

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

  def create = Action(parse.json) { request =>
    request.body.validate[JobPartial].map { case partial =>
      val job = Job.fromPartial(partial)
      JobRepository.insert(job)
      Ok(Json.toJson(job))
    }.recoverTotal{
      e => BadRequest(JsError.toJson(e.errors))
    }
  }

  def delete(id: Long) = Action {
    JobRepository.delete(id)
    Accepted
  }
}
