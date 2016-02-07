package repository

import java.util.Date
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import model.User

object UserRepository {

  import play.api.Play.current

  private val rowParser: RowParser[User] = {
    get[Long]("id") ~
      get[String]("email") ~
      get[String]("password") ~
      get[Date]("created") map { case id ~ email ~ password ~ created =>
      User(Some(id), email, password, created)
    }
  }

  def all = DB.withConnection { implicit c =>
    SQL("SELECT * FROM users").as(rowParser.*)
  }

  def findOneByID(id: Long): Option[User] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM users WHERE id = {id}").on('id -> id).as(rowParser.singleOpt)
  }

  def findOneByEmail(email: String): Option[User] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM users WHERE email = {email}").on('email -> email).as(rowParser.singleOpt)
  }

  def create(user: User) = DB.withConnection { implicit c =>
    SQL("INSERT INTO users (email, password, created) VALUES ({email}, {password}, {created})")
      .on('email -> user.email, 'password -> user.password, 'created -> user.created)
      .executeInsert()
  }

}
