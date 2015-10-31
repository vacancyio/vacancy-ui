package model

import play.api.libs.json.Json
import repository.EmployerRepository
import security.Encrypt

case class Employer(id: Option[Long], name: String, email: String, password: String, created: java.util.Date)

object Employer {

  implicit val format = Json.format[Employer]

  def authenticate(email: String, password: String): Boolean =
    EmployerRepository.findOneByEmail(email) exists { user =>
      Encrypt.checkPassword(password, user.password)
    }
}
