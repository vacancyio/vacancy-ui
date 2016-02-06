package model

case class UserProfile(
  id: Option[Long],
  uid: Long,
  name: Option[String],
  title: Option[String],
  about: Option[String],
  github: Option[String],
  website: Option[String]
)
