package model

import play.api.libs.json._
import repository.EmployerRepository

case class Job(id: Option[Long],
               title: String,
               description: String,
               skills: Option[String],
               application: Option[String],
               contract: Int,
               remote: Boolean,
               city: Option[String],
               country: String,
               employer: Long,
               created: java.util.Date = new java.util.Date) {

  def slug = Job.generateSlug(this.id, this.title)

  /**
   * Pretty print for displaying the job location
   */
  def location(): String = {
    helpers.Location.nameFromISO2(this.country)
  }

  def employerInformation: Option[Employer] =
    EmployerRepository.findOneById(this.employer)

  def contractName: String =
    ContractType.fromInt(this.contract) map (_.name) getOrElse ""

  def listingName: Option[String] =
    this.employerInformation map { employer =>
      s"${this.title} at ${employer.name}"
    }
}

object Job {

  implicit val format = Json.format[Job]

  def normalizeTitle(title: String) =
    title.toLowerCase.replace(" ", "-")

  /**
   * Pretty print for displaying the job location
   */
  def location(country: String): String = {
    helpers.Location.nameFromISO2(country)
  }

  /**
   * Generates a URL slug in the form "34-senior-software-engineer"
   *
   * @param id The job id
   * @param title The job title
   */
  def generateSlug(id: Option[Long], title: String): String = {
    val normalisedId = id.map(_.toString).getOrElse("")
    val normalisedTitle = normalizeTitle(title)
    s"$normalisedId-$normalisedTitle"
  }
}

