package forms

import play.api.data._
import play.api.data.Forms._

case class StandardJob(title: String, jobType: String)

object StandardJobForm {

  import play.api.Play.current
  import play.api.i18n.Messages.Implicits._

  val form = Form(
    mapping(
      "title"   -> text,
      "jobType" -> text
    )(StandardJob.apply)(StandardJob.unapply)
  )
}
