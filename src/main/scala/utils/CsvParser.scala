package utils


import model.{ AddressEntry, Gender }
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.util.{ Failure, Success, Try }

object CsvParser extends CsvParser {
  def getLines(path: String): Try[Seq[String]] = Try {
    io.Source.fromResource(path).getLines.toList
  }
}

trait CsvParser {

  case class ParseException(m: String) extends Exception(m)

  def getLines(path: String): Try[Seq[String]]

  def readFile: Try[Seq[AddressEntry]] = {
    for {
      lines <- getLines("addressBook.csv")
      addressBook <- parseLines(lines)
    } yield {
      addressBook
    }
  }

  def parseLines(lines: Seq[String]): Try[Seq[AddressEntry]] = Try {
    lines.zipWithIndex.map { case (line, idx) =>
      val parts = line.split(",")
      require(parts.length == 3, s"Malformed line at $idx")
      parseLine(parts) match {
        case Success(x) => x
        case Failure(e) => throw ParseException(s"Parse error at line $idx : ${e.getMessage}")
      }
    }
  }

  def parseLine(line: Seq[String]): Try[AddressEntry] = Try {
    val (name, gender, dob) = (line.head.trim, line(1).trim, line(2).trim)
    AddressEntry(name, Gender.withName(gender), DateTime.parse(dob, DateTimeFormat.forPattern("dd/MM/YY")))
  }
}

