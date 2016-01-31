package model

import java.util.Date
import play.api.libs.json._
import security.Encrypt

case class User(id: Option[Long],
                username: String,
                email: String,
                password: String,
                avatar: Option[String],
                created: Date)

object User {

  implicit val format = Json.format[User]

  def fromPartial(partialUser: UserPartial): User =
    User(None, partialUser.username, partialUser.email, Encrypt.encryptPassword(partialUser.password), None, new Date())
}