package app.controllers

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class StaticSpec extends Specification with ControllerTestHelpers {

  "The static controller" should {

    "render the index page" in new WithApplication {
      getStatus("/") must equalTo(Some(OK))
    }
  }
}
