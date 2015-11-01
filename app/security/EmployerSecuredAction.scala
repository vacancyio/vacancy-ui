package security

import controllers.routes
import repository.EmployerRepository
import model._
import play.api.mvc._

trait EmployerSecuredAction {

  private def email(request: RequestHeader): Option[String] =
    request.session.get("email")

  def onUnauthorized(request: RequestHeader): Result =
    Results.Redirect(routes.EmployerAuthentication.login())

  def withAuth(f: => String => Request[AnyContent] => Result): EssentialAction = {
    Security.Authenticated(email, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  def withEmployer(f: Employer => Request[AnyContent] => Result): EssentialAction =
    withAuth { email => implicit request =>
      EmployerRepository.findOneByEmail(email).fold(onUnauthorized(request))(f(_)(request))
  }
}
