package controllers.api

import model.Employer
import play.api.mvc._
import scala.concurrent.Future

object BasicAuthAction extends ActionBuilder[Request] with ActionFilter[Request] {

  private val unauthorized =
    Results.Unauthorized.withHeaders("WWW-Authenticate" -> "Basic realm=Unauthorized")

  def filter[A](request: Request[A]): Future[Option[Result]] = {

    val result = request.headers.get("Authorization") map { authHeader =>
      val (email, pass) = decodeBasicAuth(authHeader)
      if (Employer.authenticate(email, pass)) None else Some(unauthorized)
    } getOrElse Some(unauthorized)

    Future.successful(result)
  }

  private [this] def decodeBasicAuth(authHeader: String): (String, String) = {
    val baStr = authHeader.replaceFirst("Basic ", "")
    val decoded = new sun.misc.BASE64Decoder().decodeBuffer(baStr)
    val Array(email, password) = new String(decoded).split(":")
    (email, password)
  }
}
