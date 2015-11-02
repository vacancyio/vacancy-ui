package helpers

object Location {

  def nameFromISO2(iso2: String): String = iso2 match {
    case "GB" => "United Kingdom"
    case "US" => "United States"
    case x    => x
  }
}
