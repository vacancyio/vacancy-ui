package app.helpers

import org.specs2.mutable.Specification
import helpers.TagExtraction
import fixtures._

class TagExtractionSpec extends Specification {

  "The TagExtraction object" should {

    "extract tags for common programming languages" in {
      val tags = TagExtraction.tagsForJob(Fixtures.validJob.copy(title = "Clojure and Ruby engineer", description = ""))
      tags mustEqual List("clojure", "ruby")
    }
  }
}
