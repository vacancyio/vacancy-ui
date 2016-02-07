package model

import play.api.libs.json._

case class UserPersonalInfo(
  id: Option[Long],
  uid: Long,
  name: Option[String],
  location: Option[String],
  about: Option[String],
  phone: Option[String],
  website: Option[String]
)

object UserPersonalInfo {
  implicit val format = Json.format[UserPersonalInfo]
}
