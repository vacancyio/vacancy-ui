package model

import play.api.libs.json.Json

case class Employer(id: Option[Long], name: String, email: String, password: String, created: java.util.Date)

object Employer {

  implicit val format = Json.format[Employer]
}
