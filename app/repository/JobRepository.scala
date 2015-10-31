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
      get[Long]("employer_id") ~
      get[Date]("created") map { case id ~ title ~ description ~ skills ~ contract ~ remote ~ city ~ country ~ employer ~ created =>
        Job(Some(id), title, description, skills, contract, remote, city, country, employer, created)
    }
  }

  def all(limit: Int = 100): Seq[Job] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM jobs ORDER BY id DESC LIMIT {limit}").on('limit -> limit).as(rowParser.*)
  }

  def findOneById(id: Long): Option[Job] = DB.withConnection { implicit c =>
    SQL("SELECT * from jobs WHERE id = {id}").on('id -> id).as(rowParser.singleOpt)
  }

  def insert(employer: Employer, partial: JobPartial): Option[Long] = DB.withConnection { implicit c =>
    employer.id flatMap { id =>
      val fields = "(title, description, skills, contract, city, country, employer_id, created)"
      val values = "({title}, {description}, {skills}, {contract}, {city}, {country}, {employer}, {created})"
      SQL(s"INSERT INTO jobs $fields VALUES $values")
        .on(
          'title -> partial.title,
          'description -> partial.description,
          'skills -> partial.skills,
          'contract -> partial.contract,
          'city -> partial.city,
          'country -> partial.country,
          'employer -> id,
          'created -> new Date
        ).executeInsert()
    }
  }
}
