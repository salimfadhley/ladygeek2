package empathy.scoring

/**
  * Created by salim on 23/10/2016.
  */
class LowerScorer(name:String, target:Double) extends KeepScorer(name, target) {
  override val strategy: String = "Lower"
  override def rank(input: List[String]): Double = {
    actualScore(input) match {
      case i if i > target => 0
      case _ => super.rank(input)}
  }
}
