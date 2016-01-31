package repository

import java.util.Date
import play.api.db.DB
import anorm._
import anorm.SqlParser._
import model.User
import security.Encrypt

object UserRepository {

  import play.api.Play.current

  private val rowParser: RowParser[User] = {
    get[Long]("id") ~
      get[String]("username") ~
      get[String]("email") ~
      get[String]("password") ~
      get[Option[String]]("avatar") ~
      get[Date]("created") map { case id ~ username ~ email ~ password ~ avatar ~ created =>
      User(Some(id), username, email, password, avatar, created)
    }
  }

  def all = DB.withConnection { implicit c =>
    SQL("SELECT * FROM users").as(rowParser.*)
  }

  def create(user: User) = DB.withConnection { implicit c =>
    SQL("INSERT INTO users (username, email, password, avatar, created) VALUES {username}, {email}, {password}, {avatar}, {created}")
      .on('username -> user.username,
          'email -> user.email,
          'password -> user.password,
          'avatar -> user.avatar,
          'created -> user.created)
  }
}
