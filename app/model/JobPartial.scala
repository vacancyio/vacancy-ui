package model

case class JobPartial(title: String,
                      description: String,
                      skills: Option[String],
                      application: Option[String],
                      contract: Int,
                      remote: Boolean,
                      city: Option[String],
                      country: String)


