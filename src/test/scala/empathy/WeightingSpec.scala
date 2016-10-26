package empathy

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 18/10/2016.
  */
class WeightingSpec extends FlatSpec with Matchers{

  "Weighting" can "be created" in {
    val w = Weighting(List(1.0, 1.0, 1.0))
    assert(w.weights.head==1.0)
  }

  it can "be applied to a value" in {
    assert(Weighting(List(1.0, 1.0, 1.0))(2.0)==7)
  }

  it can "be as long as you want applied to a value" in {
    assert(Weighting(List(1.0, 1.0, 1.0, 1.0))(2.0)==15)
  }

  it can "be empty" in {
    assert(Weighting(List())(2.0)==0)
  }

  it can "be unapplied" in {
    val w = Weighting(List(1.0, 2.0, 3.0))
    w match {
      case Weighting(l) => assert(l==w.weights)
      case _ => throw new RuntimeException("Match failed!")
    }
  }

  it can "be converted to json" in {
    val w = Weighting(List(1.0, 2.0, 3.0))
    val json = w.toJson
    val text = json.toString()
    assert(text=="[1,2,3]")
  }

}
