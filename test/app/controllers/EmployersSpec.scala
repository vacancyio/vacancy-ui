package app.controllers

import controllers.routes
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class EmployersSpec extends Specification with ControllerTestHelpers {

  "The employers controller" should {

    "render the index page" in new WithApplication {
      getStatus(routes.Employers.index().url) must equalTo(Some(OK))
    }

    "render the register page" in new WithApplication {
      getStatus(routes.Employers.register().url) must equalTo(Some(OK))
    }
  }
}
