package empathy.weight

import empathy._
import play.api.libs.json.{JsArray, JsObject, JsValue, Json}

import scala.collection.immutable.Range.Inclusive
import scala.io.Source

/**
  * Created by salim on 18/10/2016.
  */
case class Weightings(weightings:Map[String,Weighting]) {
  def fitness = weightings.valuesIterator.map(_.weights.fitness).sum * -1

  def toJson:JsObject = {

    val ws = JsArray(weightings.map{
      case (s, w) => Json.obj(
        "name" -> s,
        "weights" -> w.toJson
      )
    }.toSeq)

    Json.obj("weightings"->ws)
  }


  def calculateColumnScores(row: Map[String, Double]): Map[String,Double] = {
    weightings.map{
      case (columnName: String, w: Weighting) => {
        row.get(columnName) match {
          case Some(v) => (columnName, w(v))
          case None => throw new MissingColumn(s"Row is missing column $columnName")
        }
      }
    }
  }

  def calculateScore(row: Map[String,Double]) : Double = {
     calculateColumnScores(row).valuesIterator.sum
  }

}

object Weightings {

  def make(columns: List[String], default: Double=0.0, coeficents: Int=3, fuzz: Double=0): Weightings = {
    Weightings(columns.map(cn=>{
      val w=Weighting((0 until coeficents).map(i=>default + ( util.Random.nextDouble() - 0.5 ) * fuzz).toList)
      (cn, w)
    }).toMap)
  }

  def fromJsonString(jsonString:String):Weightings = {
    fromJson(Json.parse(jsonString))
  }

  def fromJson(json:JsValue):Weightings = {
    val ws = (json \ "weightings").as[JsArray]


    val namesAndWeights: Seq[(String, Weighting)] = ws.value.map {
      case v: JsValue => {
        val vm = v.as[JsObject].value
        val name = vm("name").as[String]
        val weights: List[Double] = vm("weights").as[JsArray].value.map((x: JsValue) => x.toString.toDouble).toList
        (name, Weighting(weights))
      }
    }
    Weightings(namesAndWeights.toMap)
  }

  def fromJsonFile(inputFilename:String):Weightings = {
    val stream = Source.fromInputStream(getClass.getResourceAsStream(inputFilename))
    fromJsonString(stream.mkString)
  }
}

