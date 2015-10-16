package clients

import play.api.libs.ws.WS
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

class DispatchClient(endpoint: String) {

  def jobs() = {
    WS.url(endpoint ++ "/crawlers/stackoverflow/london").get map { response =>
      response.body
    }
  }
}

object DispatchClient {


}
