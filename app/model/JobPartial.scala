package model

case class JobPartial(title: String,
                      description: String,
                      skills: String,
                      contract: Int,
                      remote: Boolean,
                      city: Option[String],
                      country: String)


