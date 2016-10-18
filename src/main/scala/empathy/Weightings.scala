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

