import model.{ AddressEntry, Gender }
import org.joda.time.Days
import utils.CsvParser

import scala.util.{ Failure, Try }

object AddressBook extends AddressBook with App {
  lazy val entries = CsvParser.readFile


  override def main(args: Array[String]): Unit = for {
    males <- howManyMales
    oldest <- oldest
    daysOlder <- daysBetweenAAndB("Bill McKnight", "Paul Robinson")
  } yield {
    println(s"Address book contains:")
    println(s" - $males males")
    println(oldest.map(o => s" - ${o.name} is the oldest").getOrElse(" - No one is the oldest"))
    println(s" - Bill is $daysOlder days older than Paul")
  }
}

trait AddressBook {

  def entries: Try[Seq[AddressEntry]]

  def howManyMales: Try[Int] = entries.map { e =>
    e.count(_.gender == Gender.Male)
  }

  def oldest: Try[Option[AddressEntry]] = entries.map { e =>
    e.sortWith { (a, b) =>  a.dob.isBefore(b.dob) }.headOption
  }

  def daysBetweenAAndB(a: String, b: String): Try[Int] = for {
    entryA <- findEntry(a)
    entryB <- findEntry(b)
    daysBetween <- daysBetweenDob(entryA, entryB)
  } yield daysBetween.getDays

  private def findEntry(name: String): Try[AddressEntry] = entries.map { e =>
    e.find(_.name == name).getOrElse(throw NotFound(s"No entry found for $name"))
  }

  private def daysBetweenDob(a: AddressEntry, b: AddressEntry): Try[Days] = Try {
    Days.daysBetween(a.dob.toLocalDate, b.dob.toLocalDate)
  }
}

case class NotFound(m: String) extends Exception(m)
