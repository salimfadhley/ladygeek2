package empathy.ranking

/**
  * Created by salim on 23/10/2016.
  */
class KeepRanker(name:String, target:Double, exponent:Int=2) extends Ranker {
  override val strategy: String = "Keep"

  def actualScore(input:List[String]):Double = {
    input.indexOf(name) match {
      case -1 => throw new ItemNotInRanking(name)
      case i => i.toDouble / (input.size - 1).toDouble
    }
  }

  override def rank(input: List[String]): Double = Math.pow(target - actualScore(input), exponent)

}
