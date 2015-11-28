package app.helpers

import helpers.Format

import org.specs2.mutable.Specification

class FormatSpec extends Specification {

  "The pluralize method" should {
    "return zero length unchanged" in {
      Format.pluralize(1, "foo") mustEqual "foo"
      Format.pluralize(2, "bar") mustEqual "bars"
    }
  }

  "The dash method" should {
    "format a simple string with spaces" in {
      Format.dash("foo bar") mustEqual "foo-bar"
    }
  }

  "The unDash method" should {
    "format a simple string containing dashes" in {
      Format.unDash("foo-bar") mustEqual "foo bar"
    }
  }
}
