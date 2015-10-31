package repository

import java.util.Date
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import model._
import play.api.db.DB

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
    SQL(s"SELECT * FROM employers ORDER BY id DESC LIMIT {limit}")
      .on('limit -> limit).as(rowParser.*)
  }

  def insert(partial: EmployerPartial): Option[Long] = DB.withConnection { implicit c =>
    SQL(s"INSERT INTO employers (name, email, password, created) VALUES ({name}, {email}, {password}, {created})")
      .on(
        'name -> partial.name,
        'email -> partial.email,
        'password -> partial.password,
        'created -> new Date
      ).executeInsert()
  }
}
