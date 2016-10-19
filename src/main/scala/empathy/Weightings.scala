package empathy

/**
  * Created by salim on 18/10/2016.
  */
case class Weightings(weightings:Map[String,Weighting]) {
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
}

