import model.{ AddressEntry, Gender }
import org.joda.time.DateTime
import org.scalatest.{ FlatSpec, Matchers }
import utils.CsvParser

import scala.util.Success

class CsvParserSpec extends FlatSpec with Matchers {

  "Csv Parser" should "successfully read an address book file" in {
    val parser = new CsvParser {
      override def getLines(path: String) = Success(Seq("Charles Babbage,Male,12/12/89"))
    }

    val expected = Success(Seq(
      AddressEntry("Charles Babbage", Gender.Male,
        DateTime.now.withMillisOfDay(0).withDate(1989, 12, 12)
      )
    ))

    val actual = parser.readFile
    expected shouldBe actual
  }

}
