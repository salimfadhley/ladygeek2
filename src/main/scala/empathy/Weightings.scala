package empathy

import play.api.libs.json.{JsArray, JsObject, JsValue, Json}

import scala.io.Source

/**
  * Created by salim on 18/10/2016.
  */
case class Weightings(weightings:Map[String,Weighting]) {
  def toJson:JsObject = {

    val ws = JsArray(weightings.map{
      case (s, w) => Json.obj(
        "name" -> s,
        "weights" -> w.toJson
      )
    }.toSeq)

    Json.obj("weightings"->ws)
  }
  def calculateScore(row: Map[String, Double]): Double = {
    weightings.map((x: (String, Weighting)) => {
      val columnName:String = x._1
      val w:Weighting = x._2
      row.get(columnName) match {
        case Some(v) => w(v)
        case None => throw new MissingColumn(s"Row is missing column $columnName")
      }
    } ).sum
  }
}

object Weightings {
  def applyFuzz(i: Double, fuzz: Double): Double = i + ( util.Random.nextDouble() - 0.5 ) * fuzz
  def make(columns: List[String], default: Double=0.0, coefficents: Int=3, fuzz: Double=0): Weightings = {
    columns.map(c=>{
      (c, weighting((0 until coefficents).map((i: Int) =>applyFuzz(default, fuzz)).toList))
    }).toMap
  }

  def fromJsonString(jsonString:String):Weightings = {
    fromJson(Json.parse(jsonString))
  }

  def fromJson(json:JsValue):Weightings = {
    val ws = (json \ "weightings").as[JsArray]


    val namesAndWeights: Seq[(String, Weighting)] = ws.value.map {
      case v: JsValue => {
        val vm = v.as[JsObject].value
        val name = vm("name").toString
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

