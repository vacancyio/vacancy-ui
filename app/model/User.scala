package model

import java.security.MessageDigest
import java.util.Date

import play.api.libs.json._
import repository.UserRepository
import security.Encrypt

case class User(id: Option[Long],
                email: String,
                password: String,
                created: Date) {
}

object User {

  implicit val format = Json.format[User]

  def fromPartial(partialUser: UserPartial): User =
    User(None, partialUser.email, Encrypt.encryptPassword(partialUser.password), new Date())

  def authenticate(email: String, password: String): Boolean =
    UserRepository.findOneByEmail(email) exists { user =>
      Encrypt.checkPassword(password, user.password)
    }

  def gravatar(email: String): String = {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(email.trim.toLowerCase.getBytes)
    val hexDigits = "0123456789abcdef".toCharArray
    val hash = digest.foldLeft("") {
      case (xs, x) => xs + hexDigits((x >> 4) & 0xf) + hexDigits(x & 0xf)
    }
    s"http://www.gravatar.com/avatar/$hash?s=160"
  }
}