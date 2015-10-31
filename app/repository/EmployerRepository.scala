package repository

import java.util.Date
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import model._
import play.api.db.DB
import security.Encrypt

object EmployerRepository {

  private val rowParser: RowParser[Employer] = {
    get[Long]("id") ~
    get[String]("name") ~
    get[String]("email") ~
    get[String]("password") ~
    get[Date]("created") map { case id ~ name ~ email ~ password ~ created =>
      Employer(Some(id), name, email, password, created)
    }
  }

  def all(limit: Int = 100): List[Employer] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM employers ORDER BY id DESC LIMIT {limit}")
      .on('limit -> limit).as(rowParser.*)
  }

  def findOneByEmail(email: String): Option[Employer] = DB.withConnection { implicit c =>
    SQL("SELECT * from employers WHERE email = {email}").on('email -> email).as(rowParser.singleOpt)
  }

  def insert(partial: EmployerPartial): Option[Long] = DB.withConnection { implicit c =>
    SQL("INSERT INTO employers (name, email, password, created) VALUES ({name}, {email}, {password}, {created})")
      .on(
        'name -> partial.name,
        'email -> partial.email,
        'password -> Encrypt.encryptPassword(partial.password),
        'created -> new Date
      ).executeInsert()
  }
}
