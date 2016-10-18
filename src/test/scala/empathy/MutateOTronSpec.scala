package empathy

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 18/10/2016.
  */
class MutateOTronSpec extends FlatSpec with Matchers {

  "MutateOTron" should "be able to flip random bits of a weighting" in {
    val m = new MutateOTron()
    val w:Weighting = List(1.0)
    val r = m.flipACoefficient(w)
    assert(r.weights.head == -1.0)
  }

  it should "be able to jumble random bits of a weighting" in {
    val m = new MutateOTron()
    val w:Weighting = List(1.0)
    val r = m.jumble(w)
    assert(r.weights.head == 1.0)
  }

  it should "be able to tweak a random value" in {
    val m = new MutateOTron()
    val w:Weighting = List(1.0)
    val r = m.tweak(w)
    assert(r.weights.head != 1.0)
  }

  it should "not tweak if the tweak-range is zero" in {
    val m = new MutateOTron()
    val w:Weighting = List(1.0)
    val r = m.tweak(w, tweak_range = 0.0)
    assert(r.weights.head == 1.0)
  }

}
