package app.analysis

import analysis.JobAnalysis
import org.specs2.mutable.Specification
import model.Job

class JobAnalysisSpec extends Specification {

  "The JobAnalysis.contains method" should {

    "return true if a string contains a given input pattern" in {
      JobAnalysis.contains("Frameworks like Ruby on Rails are used", "ruby on rails") mustEqual true
    }
  }
}
