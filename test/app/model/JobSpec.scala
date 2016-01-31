package app.model

import model.Job

import org.specs2.mutable.Specification

class JobSpec extends Specification {

  "Normalizing a job title" should {

    "remove bad chars" in {
      Job.normalizeTitle("Hello, world") mustEqual "hello-world"
    }
  }
}
