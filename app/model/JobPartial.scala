package model

case class JobPartial(title: String,
                      description: String,
                      skills: String,
                      contract: Int,
                      companyName: Option[String],
                      companyWebsite: Option[String],
                      city: Option[String],
                      country: String)


