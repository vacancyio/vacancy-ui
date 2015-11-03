package helpers

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.twirl.api.HtmlFormat

object Format {
  /**
   * View pluralization helper
   *
   * @param n The number of items
   * @param s A string to pluralize
   */
  def pluralize(n: Int, s: String) = if (n == 1) s else s ++ "s"

  def humanDate(date: java.util.Date): String = {
    val format = DateTimeFormat.forPattern("d MMM")
    format.print(new DateTime(date))
  }

  def formatInput(input: String): String =
    HtmlFormat.escape(input).toString().replace("\n", "<br />")
}
