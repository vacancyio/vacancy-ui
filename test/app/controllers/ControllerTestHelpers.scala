package app.controllers

trait ControllerTestHelpers {

  import play.api.test._
  import play.api.test.Helpers._

  def getStatus(url: String): Option[Int] = route(FakeRequest(GET, url)) map status

}
