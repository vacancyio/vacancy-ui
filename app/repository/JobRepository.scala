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
      get[Option[String]]("skills") ~
      get[Option[String]]("application") ~
      get[Int]("contract") ~
      get[Boolean]("remote") ~
      get[Option[String]]("city") ~
      get[String]("country") ~
      get[Long]("employer_id") ~
      get[Date]("created") map { 
        case id ~ title ~ description ~ skills ~ apply ~ contract ~ remote ~ city ~ country ~ employer ~ created =>
          Job(Some(id), title, description, skills, apply, contract, remote, city, country, employer, created)
    }
  }

  def all(limit: Int = 100): List[Job] = DB.withConnection { implicit c =>
    SQL("""SELECT * FROM jobs WHERE created > current_date - interval '60 days' ORDER BY id DESC LIMIT {limit}""")
      .on('limit -> limit)
      .as(rowParser.*)
  }

  def search(query: String) = DB.withConnection { implicit c =>
    SQL(s"""SELECT * FROM jobs WHERE lower(title) LIKE '${"%" + query.toLowerCase + "%"}' OR lower(description) LIKE '${"%" + query.toLowerCase + "%"}' OR lower(skills) LIKE '${"%" + query.toLowerCase + "%"}'""")
      .as(rowParser.*)
  }

  def findOneById(id: Long): Option[Job] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM jobs WHERE id = {id}").on('id -> id).as(rowParser.singleOpt)
  }

  def findOneByIdForEmployer(employer: Long, id: Long): Option[Job] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM jobs WHERE id={id} AND employer_id = {employer}")
      .on('id -> id, 'employer -> employer)
      .as(rowParser.singleOpt)
  }

  /**
   * Insert a job into the database
   *
   * A single job is always associated with an employer
   *
   * @param employer An employer submitting this job
   * @param partial The partial form data for this job
   */
  def insert(employer: Employer, partial: JobPartial): Option[Long] = DB.withConnection { implicit c =>
    employer.id flatMap { id =>
      val fields = "(title, description, skills, application, contract, city, country, employer_id, created)"
      val values = "({title}, {description}, {skills}, {application}, {contract}, {city}, {country}, {employer}, {created})"
      SQL(s"INSERT INTO jobs $fields VALUES $values")
        .on(
          'title -> partial.title.capitalize,
          'description -> partial.description,
          'skills -> partial.skills,
          'application -> partial.application,
          'contract -> partial.contract,
          'city -> partial.city,
          'country -> partial.country,
          'employer -> id,
          'created -> new Date
        ).executeInsert()
    }
  }

  // TODO not working !
  def update(id: Long, partial: JobPartial): Int = DB.withConnection { implicit c =>
      SQL(
        """UPDATE jobs SET title = {title}, description = {description}, skills = {skills}, application = {application},
          |contract = {contract}, remote = {remote}, city = {city}, country = {country} WHERE id = {id}""".stripMargin).on(
        'id    -> id,
        'title -> partial.title,
        'description -> partial.description,
        'skills -> partial.skills,
        'application -> partial.application,
        'contract -> partial.contract,
        'remote -> partial.remote,
        'city -> partial.city,
        'country -> partial.country
      ).executeUpdate()
  }

  def forEmployer(id: Long): List[Job] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM jobs WHERE employer_id = {id}").on('id -> id).as(rowParser.*)
  }
}
