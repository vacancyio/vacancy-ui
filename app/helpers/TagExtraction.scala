package helpers

import model.Job

object TagExtraction {
  private val languages = List(
    "clojure",
    "haskell",
    "scala",
    "javascript",
    "ruby",
    "golang",
    "php",
    "c#",
    "c++",
    "python",
    "erlang",
    "ocaml"
  )

  private val frameworks = List(
    "rails",
    "django"
  )

  private val databases = List(
    "mysql",
    "postgres",
    "redis",
    "mongodb",
    "couchdb",
    "riak"
  )

  val tags = languages ++ frameworks ++ databases

  def tagsForJob(job: Job): List[String] =
    tags.filter(tag => job.title.toLowerCase.contains(tag) || job.description.toLowerCase.contains(tag))
}
