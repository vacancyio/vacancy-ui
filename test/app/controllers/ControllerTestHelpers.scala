package app.controllers

import play.api.test._
import play.api.test.Helpers._
  
trait ControllerTestHelpers {

  def getStatus(url: String): Option[Int] = 
    route(FakeRequest(GET, url)) map status
}
