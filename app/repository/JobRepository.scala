package repository

import anorm.SqlParser._
import anorm._
import model._
import play.api.Play.current
import play.api.db.DB
import java.util.Date

object JobRepository {

  private val rowParser: RowParser[Job] = {
    get[Long]("id") ~
      get[String]("title") ~
      get[String]("description") ~
      get[String]("skills") ~
      get[Int]("contract") ~
      get[Boolean]("remote") ~
      get[Option[String]]("city") ~
      get[String]("country") ~
      get[Long]("employer") ~
      get[Date]("created") map { case id ~ title ~ description ~ skills ~ contract ~ remote ~ city ~ country ~ employer ~ created =>
      Job(Some(id), title, description, skills, contract, remote, city, country, employer, created)
    }
  }

  /**
   * Return all incidents from the database
   */
  def all(limit: Int = 100): Seq[Job] = DB.withConnection { implicit c =>
    SQL(s"SELECT * FROM jobs ORDER BY id DESC LIMIT {limit}")
      .on('limit -> limit).as(rowParser.*)
  }

  def insert(partial: JobPartial): Option[Long] = DB.withConnection { implicit c =>
    SQL(s"INSERT INTO jobs (title, description, skills, contract, city, country, created) VALUES ({title}, {description}, {skills}, {contract}, {city}, {country}, {created})")
      .on(
        'title -> partial.title,
        'description -> partial.description,
        'skills -> partial.skills,
        'contract -> partial.contract,
        'city -> partial.city,
        'country -> partial.country,
        'created -> new Date
      ).executeInsert()
  }
}
