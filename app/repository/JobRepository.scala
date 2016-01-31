package repository

import anorm.SqlParser._
import anorm._
import model._
import play.api.Play.current
import play.api.db.DB
import java.util.Date

object JobRepository {

  val jobsPerPage = 25

  private val rowParser: RowParser[Job] = {
    get[Long]("id") ~
    get[String]("title") ~
    get[String]("description") ~
    get[String]("employer") ~
    get[String]("location") ~
    get[Option[String]]("application") ~
    get[Option[String]]("salary") ~
    get[Boolean]("remote") ~
    get[Boolean]("contract") ~
    get[Date]("created") map { case id ~ title ~ description ~ employer ~ location ~ application ~ salary ~ remote ~ contract ~ created =>
      Job(Some(id), title, description, employer, location, application, salary, remote, contract, created)
    }
  }

  def all(page: Int = 1): List[Job] = DB.withConnection { implicit c =>
    val safePage = (if (page <= 0) 1 else page) - 1
    SQL("""SELECT * FROM jobs WHERE created > current_date - interval '14 days' ORDER BY id DESC LIMIT {perPage} OFFSET {offset}""")
      .on('offset -> safePage * jobsPerPage, 'perPage -> jobsPerPage)
      .as(rowParser.*)
  }

  def totalJobs: Long = DB.withConnection { implicit c =>
    SQL("SELECT count(*) FROM jobs").executeQuery().as(scalar[Long].single)
  }

  def findOneById(id: Long): Option[Job] = DB.withConnection { implicit c =>
    SQL("SELECT * FROM jobs WHERE id = {id}")
      .on('id -> id).as(rowParser.singleOpt)
  }

  def insert(job: Job): Option[Long] = DB.withConnection { implicit c =>
    SQL(
      """INSERT INTO jobs (title, description, employer, location, application, salary, remote, contract, created)
        |VALUES ({title}, {description}, {employer}, {location}, {application}, {salary}, {remote}, {contract}, {created})""".stripMargin)
      .on('title -> job.title,
          'description -> job.description,
          'employer -> job.employer,
          'location -> job.location,
          'application -> job.application,
          'salary -> job.salary,
          'remote -> job.remote,
          'contract -> job.contract,
          'created -> new Date).executeInsert()
    }

  def update(job: Job): Option[Int] = DB.withConnection { implicit c =>
    job.id map { id =>
      val query = SQL(
        """
          |UPDATE jobs SET title={title}, description={description}, employer={employer}, location={location},
          |application={application}, salary={salary}, remote={remote}, contract={contract}
          |WHERE id = {id}
        """.stripMargin).on(
        'id -> id,
        'title -> job.title,
        'description -> job.description,
        'employer -> job.employer,
        'location -> job.location,
        'application -> job.application,
        'salary -> job.salary,
        'remote -> job.remote,
        'contract -> job.contract)
      query.executeUpdate()
    }
  }

  def delete(id: Long) = DB.withConnection { implicit c =>
    SQL("DELETE FROM jobs WHERE id = {id}").on('id -> id).execute()
  }

  def search(query: String) = DB.withConnection { implicit c =>
    SQL(s"""SELECT * FROM jobs WHERE lower(title) LIKE '${"%" + query.toLowerCase + "%"}' OR lower(description) LIKE '${"%" + query.toLowerCase + "%"}'""")
      .as(rowParser.*)
  }
}
