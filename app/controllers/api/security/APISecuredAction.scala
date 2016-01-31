package controllers.api.security

import play.api.Play
import play.api.mvc._

import scala.concurrent.Future

class AuthenticatedRequest[A](request: Request[A]) extends WrappedRequest[A](request)

/**
  * This can be used to implement HTTP basic authentication for API requests
  *
  * def index = APISecuredAction { OK }
  */
object APISecuredAction extends ActionBuilder[AuthenticatedRequest] {

  import play.api.Play.current

  lazy val apiUsername = Play.application.configuration.getString("vacancy.api.username")
  lazy val apiPassword = Play.application.configuration.getString("vacancy.api.password")

  def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[Result]) = {
    request.headers.get("Authorization") flatMap { authHeader=>
      val (username, password) = decodeBasicAuth(authHeader)
      if (apiUsername.contains(username) && apiPassword.contains(password))
        Some(block(new AuthenticatedRequest(request)))
      else None
    } getOrElse unauthorized
  }

  private val unauthorized =
    Future.successful(
      Results.Unauthorized
        .withHeaders("WWW-Authenticate" -> "Basic realm=Unauthorized"))

  private [this] def decodeBasicAuth(authHeader: String): (String, String) = {
    val baStr = authHeader.replaceFirst("Basic ", "")
    val decoded = new sun.misc.BASE64Decoder().decodeBuffer(baStr)
    val Array(email, password) = new String(decoded).split(":")
    (email, password)
  }
}