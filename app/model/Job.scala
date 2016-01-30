package model

import play.api.libs.json._
import java.util.Date

case class Job(id: Option[Long],
               title: String,
               description: String,
               employer: String,
               location: String,
               application: Option[String],
               salary: Option[String],
               remote: Boolean,
               contract: Boolean,
               created: Date = new Date)

object Job {

  implicit val format = Json.format[Job]

  def fromPartial(partial: JobPartial) =
    Job(None, partial.title, partial.description, partial.employer, partial.location, partial.application, partial.salary, partial.remote, partial.contract)

  def normalizeTitle(title: String) =
    title.toLowerCase.replace(" ", "-")

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

