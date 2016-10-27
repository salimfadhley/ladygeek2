import empathy.weight.{Weighting, Weightings}

package object empathy {

  type inputRow = Map[String,Double]

  implicit def stringToConvertible(s: String): ConvertibleThing = {
    ConvertibleThing(s)
  }

  implicit def stringMapToConvertibleMap(inp: Map[String, String]): Map[String, ConvertibleThing] = {
    inp.map((x: (String, String)) => (x._1, ConvertibleThing(x._2)))
  }


}
