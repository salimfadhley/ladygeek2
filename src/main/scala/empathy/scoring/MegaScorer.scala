package empathy.scoring

/**
  * Created by salim on 24/10/2016.
  */
case class MegaScorer(scorers:List[Scorer]) {
  def score(ind: List[String]) = {
    scorers.map((s) => s.rank(ind)).sum * -1
  }
}
