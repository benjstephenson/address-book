import model.{ AddressEntry, Gender }
import utils.CsvParser

import scala.util.Try

object AddressBook extends AddressBook with App {
  lazy val entries = CsvParser.readFile


  override def main(args: Array[String]): Unit = for {
    males <- howManyMales
  } yield {
    println(s"Address book contains:")
    println(s" - $males males")
  }
}

trait AddressBook {
  def entries: Try[Seq[AddressEntry]]

  def howManyMales: Try[Int] = entries.map { e =>
    e.count(_.gender == Gender.Male)
  }
}
