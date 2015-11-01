package validation

import scalaz._, Scalaz._

object CountryCodeValidator extends Validator {

  def validate(input: String) = input.right
}
