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
    get[Int]("credits") ~
    get[Date]("created") map {
      case id ~ name ~ email ~ password ~ credits ~ created =>
        Employer(Some(id), name, email, password, credits, created)
    }
  }

  def all(limit: Int = 100): List[Employer] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM employers ORDER BY id DESC LIMIT {limit}")
      .on('limit -> limit).as(rowParser.*)
  }

  def findOneById(id: Long): Option[Employer] = DB.withConnection { implicit c =>
    SQL("SELECT * from employers WHERE id = {id}").on('id -> id).as(rowParser.singleOpt)
  }

  def findOneByEmail(email: String): Option[Employer] = DB.withConnection { implicit c =>
    SQL("SELECT * from employers WHERE email = {email}").on('email -> email).as(rowParser.singleOpt)
  }

  /**
   * Insert a [[model.Employer]] into the database
   *
   * @param partial
   * @return
   */
  def insert(partial: EmployerPartial): Option[Long] = DB.withConnection { implicit c =>
    SQL("INSERT INTO employers (name, email, password, created) VALUES ({name}, {email}, {password}, {created})")
      .on(
        'name -> partial.name,
        'email -> partial.email,
        'password -> Encrypt.encryptPassword(partial.password),
        'created -> new Date
      ).executeInsert()
  }

  /**
   * Returns the number of credits for a [[model.Employer]]
   *
   * @param id The employer primary key ID
   */
  def numberOfCredits(id: Long): Int = DB.withConnection { implicit c =>
    SQL("SELECT credits FROM employers where id = {id}").on('id -> id).as(scalar[Int].single)
  }

  /**
   * Give an exiting employer 1 credit. This would be done typically after a
   * purchase order of additional credits
   *
   * @param id The employer primary key ID
   */
  def incrementCredits(id: Long, amount: Int = 1) = DB.withConnection { implicit c =>
    SQL("UPDATE employers SET credits = credits + {amount} WHERE id = {id}")
      .on('id -> id, 'amount -> amount)
      .executeUpdate()
  }
}
