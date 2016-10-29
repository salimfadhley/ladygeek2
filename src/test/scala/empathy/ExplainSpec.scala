package empathy

import empathy.weight.Weightings
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 29/10/2016.
  */
class ExplainSpec extends FlatSpec with Matchers {

  "Explainr" should "be able to group scores" in {
    val w:Weightings = Map("A"->List(1.0), "B"->List(1.0))
    val g = Map("xx" -> Set("A", "B"))
    val row = Map("A"->2.0, "B"->3.0)

    val rf = new RankingFitness((strings: List[String]) =>0.0)

    val result:Map[String,Double] = rf.explainRows(row=row, weightings=w, explainMap=g)

  }



}
