package model

case class JobPartial(title: String,
                      description: String,
                      skills: String,
                      contract: Int,
                      remote: Boolean,
                     // TODO move into separate type
                      companyName: Option[String],
                      companyWebsite: Option[String],
                     // TODO move into location object
                      city: Option[String],
                      country: String)


