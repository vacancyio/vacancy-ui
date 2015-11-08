package app.controllers.api

import app.controllers.ControllerTestHelpers
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class JobsSpec extends Specification with ControllerTestHelpers {

  "The jobs api controller" should {

    "return all jobs" in new WithApplication {
      val response = route(FakeRequest(GET, "/api/v1/jobs"))
      response map status mustEqual Some(OK)
    }

    "return a single job by id" in new WithApplication {
      val response = route(FakeRequest(GET, "/api/v1/jobs/1"))
      // response map status mustEqual Some(OK)
    }
  }
}
