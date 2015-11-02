package model

case class Location(city: String, country: String) {
  assert(this.country.length == 3)
}
