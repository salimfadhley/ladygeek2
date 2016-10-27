package empathy

import java.io.InputStream

import com.github.tototoshi.csv.CSVReader

import scala.io.Source

/**
  * Created by sal on 17/10/16.
  */
class SourceData {
  lazy val targets:List[(String, String)] = SourceData.loadTargets(Config.target_file)
  lazy val mixed_data:SourceData.MixedData = SourceData.loadData(Config.main_input_data)

  def getColumns: List[String] = {
    mixed_data.valuesIterator.flatMap(_.keysIterator).toSet.toList
  }
}

object SourceData {
  type MixedData = Map[String,Map[String,Double]]
  def extractDouble(input: Map[String, ConvertibleThing], s: String):Double = {
    val item = input(s)
    try {
      item.d
    } catch {
      case x:RuntimeException => throw new RuntimeException(s"Error converting column $s / '${item._s}' to Double")
    }
  }


  def convertRow(input:Map[String,ConvertibleThing]):(String,Map[String,Double]) = {
    val company:String = input("company").s
    println(s"Converting $company")
    val fields = input.keys.filterNot(_=="company")
    val items = fields.map{(s: String) => (s, SourceData.extractDouble(input, s))
    }.toMap
    (company, items)
  }

  def loadData(inputFilename:String): MixedData = {
    val stream = Option(getClass.getResourceAsStream(inputFilename))
    val data = stream match {
      case None => throw new RuntimeException(s"$inputFilename is bogus!")
      case Some(s:InputStream) => Source.fromInputStream(s)
    }
    CSVReader.open(data).iteratorWithHeaders.map(stringMapToConvertibleMap).map(convertRow).toMap
  }

  def loadTargets(inputFilename: String): List[(String, String)] = {
    val stream = Option(getClass.getResourceAsStream(inputFilename))
    val data = stream match {
      case None => throw new RuntimeException(s"$inputFilename is bogus!")
      case Some(s:InputStream) => Source.fromInputStream(s)
    }
    CSVReader.open(data).iteratorWithHeaders.map(x => (x.getOrElse("Company", "Ignore"), x.getOrElse("Strategy", "XXX"))).toList
  }

}
