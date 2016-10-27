package empathy.weight

import play.api.libs.json._


/**
  * Created by sal on 17/10/16.
  */
case class Weighting(weights:List[Double]) {
  def apply(input:Double):Double = {
    weights.zip(Stream.from(0)).map(p=>Math.pow(input, p._2)*p._1).sum
  }
  def toJson:JsValue = JsArray(weights.map((d: Double) => JsNumber(d)))

  def fitness:Double = {
    weights.count(_!=0.0) match {
      case 0 => 1.0
      case _ => 0.0
    }
  }
}

object Weighting {
  def fromJson(values: JsArray):Weighting = ???
}
