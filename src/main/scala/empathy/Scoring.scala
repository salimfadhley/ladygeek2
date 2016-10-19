package empathy

/**
  * Created by salim on 19/10/2016.
  */
object Scoring {

  def scoreRow(row: Map[String,Double], weightings: Weightings): Double = weightings.calculateScore(row)

  def scoreRows(rows:Map[String, Map[String,Double]], weightings: Weightings):Map[String, Double] = {
    rows.map((x: (String, Map[String, Double])) => {
      val name = x._1
      val row = x._2

      (name, weightings.calculateScore(row))
    })
  }


}
