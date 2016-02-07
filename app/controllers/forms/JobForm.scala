package controllers.forms

import model.JobPartial
import play.api.data.Form
import play.api.data.Forms._

trait JobForm {

  val jobForm = Form(
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText,
      "employer" -> nonEmptyText,
      "location" -> nonEmptyText,
      "application" -> optional(text),
      "salary" -> optional(text),
      "remote" -> boolean,
      "contract" -> boolean
    )(JobPartial.apply)(JobPartial.unapply)
  )
}
