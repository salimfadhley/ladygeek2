package empathy

import empathy.weight.Weightings
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 19/10/2016.
  */
class ScoringSpec extends FlatSpec with Matchers {

  "Scoring" should "be able to calculate the score for a row"  in {
    val row:Map[String,Double] = Map(
      "A"->1.0,
      "B"->2.0,
      "C"->3.0
    )
    val rf = new RankingFitness(null)
    val w:Weightings = Weightings.make(columns=List("A", "B", "C"), default = 2.0, coefficents = 2, fuzz = 0.0)
    val res:Double = rf.scoreRow(row=row, weightings=w)
    assert(res==18.0)
  }


}
