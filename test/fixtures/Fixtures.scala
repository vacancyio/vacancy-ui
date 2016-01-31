package fixtures

import model.Job

object Fixtures {

  val validJob = Job(
    id = None,
    title = "Senior Software Engineer",
    description = "We require a senior software engineer working with Scala for a new startup",
    employer = "Pinterest",
    location = "Palo Alto, California",
    application = None,
    salary = None,
    remote = false,
    contract = false
  )
}
