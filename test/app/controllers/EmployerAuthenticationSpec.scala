package app.controllers

import controllers.routes
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class EmployerAuthenticationSpec extends Specification with ControllerTestHelpers {

  "The employer authentication controller" should {
    "render the login page" in new WithApplication {
      getStatus(routes.EmployerAuthentication.login().url) must equalTo(Some(OK))
    }
  }
}
