package model

import play.api.libs.json._

case class Job(id: Option[Long],
               title: String,
               description: String,
               skills: String,
               contract: Int,
               remote: Boolean,
               city: Option[String],
               country: String,
               employer: Long,
               created: java.util.Date = new java.util.Date) {

  /**
   * Pretty print for displaying the job location
   *
   */
  def location(): String = {
    this.city map { _ ++ ", " ++ this.country } getOrElse this.country
  }
}

object Job {
  implicit val format = Json.format[Job]
}

