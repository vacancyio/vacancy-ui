package validation

import scalaz._, Scalaz._

trait Validator {

  type ValidationError = String

  def validate(input: String): List[ValidationError] \/ String
}
