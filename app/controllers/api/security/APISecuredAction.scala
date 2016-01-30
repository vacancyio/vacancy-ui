package controllers.api.security
//
//import model.Employer
//import play.api.mvc._
//import repository.EmployerRepository
//
//import scala.concurrent.Future
//
//class AuthenticatedRequest[A](val employer: Employer, request: Request[A]) extends WrappedRequest[A](request)
//
//object APISecuredAction extends ActionBuilder[AuthenticatedRequest] {
//  def invokeBlock[A](request: Request[A], block: (AuthenticatedRequest[A]) => Future[Result]) = {
//    request.headers.get("Authorization") flatMap { authHeader=>
//      val (email, pass) = decodeBasicAuth(authHeader)
//      if (Employer.authenticate(email, pass))
//        EmployerRepository.findOneByEmail(email) map { employer =>
//          block(new AuthenticatedRequest(employer, request))
//        }
//      else None
//    } getOrElse unauthorized
//  }
//
//  private val unauthorized =
//    Future.successful(
//      Results.Unauthorized
//        .withHeaders("WWW-Authenticate" -> "Basic realm=Unauthorized"))
//
//  private [this] def decodeBasicAuth(authHeader: String): (String, String) = {
//    val baStr = authHeader.replaceFirst("Basic ", "")
//    val decoded = new sun.misc.BASE64Decoder().decodeBuffer(baStr)
//    val Array(email, password) = new String(decoded).split(":")
//    (email, password)
//  }
//}