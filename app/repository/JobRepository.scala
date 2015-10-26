package repository

import java.util.Date

import anorm.SqlParser._
import anorm._
import model._
import play.api.Play.current
import play.api.db.DB

object JobRepository {

  private val rowParser: RowParser[Job] = {
    get[Long]("id") ~
    get[String]("title") ~
    get[String]("description") ~
    get[String]("skills") ~
    get[Int]("contract") ~
    get[Date]("created") map {  case id ~ title ~ description ~ skills ~ contract ~ created =>
      Job(Some(id), title, description, skills, contract, created)
    }
  }

  /**
   * Return all incidents from the database
   */
  def all(limit: Int = 100): Seq[Job] = DB.withConnection { implicit c =>
    SQL(s"SELECT * FROM jobs ORDER BY id DESC LIMIT {limit}")
      .on('limit -> limit).as(rowParser.*)
  }
}
