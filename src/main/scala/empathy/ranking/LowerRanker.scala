package empathy.ranking

/**
  * Created by salim on 23/10/2016.
  */
class LowerRanker(name:String, target:Double) extends KeepRanker(name, target) {

  override def rank(input: List[String]): Double = {
    actualScore(input) match {
      case i if i > target => 0
      case _ => super.rank(input)}
  }
}
