import model.{ AddressEntry, Gender }
import org.joda.time.DateTime
import org.scalatest.{ FlatSpec, Matchers }

import scala.util.{ Failure, Success }

class AddressBookSpec extends FlatSpec with Matchers {

  val addressBook = new AddressBook {
    override def entries = Success(List(
      AddressEntry("Charles Babbage", Gender.Male, DateTime.now.withMillisOfDay(0).withDate(1889, 12, 12)),
      AddressEntry("Ada Lovelace", Gender.Female, DateTime.now.withMillisOfDay(0).withDate(1889, 10, 2)),
      AddressEntry("Alan Turing", Gender.Male, DateTime.now.withMillisOfDay(0).withDate(1969, 10, 2)),
      AddressEntry("Grace Hopper", Gender.Female, DateTime.now.withMillisOfDay(0).withDate(1999, 10, 2))
    ))
  }

  "Address book" should "find the right number of males" in {
    addressBook.howManyMales shouldBe 2
  }

  it should "find the oldest person" in {
    addressBook.oldest shouldBe
      Success(AddressEntry("Ada Lovelace", Gender.Female, DateTime.now.withMillisOfDay(0).withDate(1889, 10, 2)))
  }

  it should "find how many days older one person is than another" in {
    addressBook.daysBetweenAAndB("Ada Lovelace", "Charles Babbage") shouldBe Success(71)
  }

  it should "throw an exception if the entry is not found" in {
    addressBook.daysBetweenAAndB("someone", "someoneElse") shouldBe Failure(NotFound("No entry found for someone"))
  }

  it should "cope with an empty address book" in {
    val addressBook = new AddressBook {
      override def entries = Success(Nil)
    }

    addressBook.howManyMales shouldBe Success(0)
    addressBook.oldest shouldBe Success(None)
  }

}
