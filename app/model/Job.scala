package model

import play.api.libs.json._

case class Job(id: Option[Long],
               title: String,
               description: String,
               skills: String,
               contract: Int,
               created: java.util.Date)

object Job {
  implicit val format = Json.format[Job]
}

