package model

import org.joda.time.DateTime

object Gender extends Enumeration {
  val Male, Female = Value
}

case class AddressEntry(
  name: String,
  gender: Gender.Value,
  dob: DateTime
)
