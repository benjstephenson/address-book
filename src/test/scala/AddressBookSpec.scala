import model.{ AddressEntry, Gender }
import org.joda.time.DateTime
import org.scalatest.{ FlatSpec, Matchers }

import scala.util.Success

class AddressBookSpec extends FlatSpec with Matchers {

  val addressBook = new AddressBook {
    override def entries = Success(List(
      AddressEntry("Charles Babbage", Gender.Male, DateTime.now.withMillisOfDay(0).withDate(1889, 12, 12)),
      AddressEntry("Ada Lovelace", Gender.Female, DateTime.now.withMillisOfDay(0).withDate(1889, 10, 02)),
      AddressEntry("Alan Turing", Gender.Male, DateTime.now.withMillisOfDay(0).withDate(1969, 10, 02)),
      AddressEntry("Grace Hopper", Gender.Female, DateTime.now.withMillisOfDay(0).withDate(1999, 10, 02))
    ))
  }

  "Address book" should "find the right number of males" in {
    addressBook.howManyMales shouldBe 2
  }

}
