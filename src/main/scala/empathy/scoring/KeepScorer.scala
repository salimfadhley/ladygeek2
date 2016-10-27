package empathy.scoring

/**
  * Created by salim on 23/10/2016.
  */
class KeepScorer(name:String, target:Double, exponent:Int=2, multiplier:Double=1.0) extends Scorer {
  override val strategy: String = "Keep"

  def actualScore(input:List[String]):Double = {
    input.indexOf(name) match {
      case -1 => {
        throw new ItemNotInRanking(name)
      }
      case i => i.toDouble / (input.size - 1).toDouble
    }
  }

  override def rank(input: List[String]): Double = {
    val actual = actualScore(input)
    Math.pow(target - actual, exponent) * multiplier
  }

}
