package model

import play.api.libs.json._

case class JobPartial(
  title: String,
  description: String,
  employer: String,
  location: String,
  application: Option[String],
  salary: Option[String],
  remote: Boolean,
  contract: Boolean)

object JobPartial {
  implicit val format = Json.format[JobPartial]
}
