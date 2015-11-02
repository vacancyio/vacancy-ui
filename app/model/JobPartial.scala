package model

import play.api.libs.json._

case class JobPartial(title: String,
                      description: String,
                      skills: Option[String],
                      application: Option[String],
                      contract: Int,
                      remote: Boolean,
                      city: Option[String],
                      country: String)

object JobPartial {
  implicit val format = Json.format[JobPartial]
}
