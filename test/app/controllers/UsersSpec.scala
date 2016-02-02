package app.controllers

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class UsersSpec extends Specification with ControllerTestHelpers {

  "The users controller" should {

    "render the signup page" in new WithApplication {
      getStatus("/signup") must equalTo(Some(OK))
    }

    "render the login page" in new WithApplication {
      getStatus("/login") must equalTo(Some(OK))
    }
  }
}
