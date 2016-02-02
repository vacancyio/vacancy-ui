package model

import java.util.Date

import play.api.libs.json._
import repository.UserRepository
import security.Encrypt

case class User(id: Option[Long],
                email: String,
                password: String,
                avatar: Option[String],
                created: Date)

object User {

  implicit val format = Json.format[User]

  def fromPartial(partialUser: UserPartial): User =
    User(None, partialUser.email, Encrypt.encryptPassword(partialUser.password), None, new Date())

  def authenticate(email: String, password: String): Boolean =
    UserRepository.findOneByEmail(email) exists { user =>
      Encrypt.checkPassword(password, user.password)
    }
}