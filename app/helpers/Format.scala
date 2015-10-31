package helpers

object Format {

  /**
   * View pluralization helper
   *
   * @param n The number of items
   * @param s A string to pluralize
   */
  def pluralize(n: Int, s: String) = if (n == 0) s else s ++ "s"
}
