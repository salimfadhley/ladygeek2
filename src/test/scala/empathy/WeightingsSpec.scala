package empathy

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 18/10/2016.
  */
class WeightingsSpec extends FlatSpec with Matchers {

  "Weightings" should "be makable from simple objects" in {
    val _: Weightings = weightings(Map("A" -> List(1.0, 1.0, 1.0), "B" -> List(0.0, 0.0, 0.0)))
  }

  it should "be ale to calculate a score for a row" in {
    val w: Weightings = weightings(Map("A" -> List(0.0, 1.0, 0.0), "B" -> List(0.0, 0.0, 1.0)))
    val row:Map[String,Double] = Map("A"->1.0, "B"->2.0)
    val result:Double = w.calculateScore(row)
    assert(result==5.0)
  }

  it should "throw an error when a column is missing" in {
    val w: Weightings = weightings(Map("A" -> List(0.0, 1.0, 0.0), "B" -> List(0.0, 0.0, 1.0)))
    val row:Map[String,Double] = Map("A"->1.0, "X"->2.0)
    intercept[MissingColumn] {
      w.calculateScore(row)
    }
  }


}
