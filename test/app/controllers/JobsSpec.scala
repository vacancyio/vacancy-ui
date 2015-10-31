package app.controllers

import controllers.routes
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class JobsSpec extends Specification with ControllerTestHelpers {

  "The jobs controller" should {

    "render the job overview page" in new WithApplication {
      getStatus(routes.Jobs.index().url) must equalTo(Some(OK))
    }
  }
}
