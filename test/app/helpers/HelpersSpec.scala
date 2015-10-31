package app.helpers

import helpers.Format

import org.specs2.mutable.Specification

class HelpersSpec extends Specification {

  "The pluralize method" should {
    
    "return zero length unchanged" in {
      
      Format.pluralize(1, "foo") mustEqual "foo"
      Format.pluralize(2, "bar") mustEqual "bars"
    }
  }
}
