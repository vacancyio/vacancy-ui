package app.analysis

import analysis.JobAnalysis
import org.specs2.mutable.Specification

class JobAnalysisSpec extends Specification {

  "The JobAnalysis.contains method" should {
    "return true if a string contains a given input pattern" in {
      JobAnalysis.contains("Frameworks like Ruby on Rails are used", "ruby on rails") mustEqual true
    }
  }

  "The JobAnalysis.extract method" should {
    "extract any matching tags from an input string" in {
      val tags = JobAnalysis.extract("Hello ruby on rails and scala", Set("ruby on rails", "scala"))
      tags.size mustEqual 2
      tags.contains("ruby on rails") mustEqual true
      tags.contains("scala") mustEqual true
    }
  }
}
