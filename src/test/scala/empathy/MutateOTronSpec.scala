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

  it should "be not flip an empty weighting" in {
    val m = new MutateOTron()
    val w:Weighting = List()
    val r = m.flipACoefficient(w)
    assert(w.weights == Nil)
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

  it should "not tweak an empty sequence" in {
    val m = new MutateOTron()
    val w:Weighting = List()
    val r = m.tweak(w, tweak_range = 9.0)
    assert(r.weights == List())
  }

  it should "be ale to add an extra coefficient" in {
    val m = new MutateOTron()
    val w:Weighting = List(1.0)
    val r = m.extend(w, tweak_range = 0.0)
    assert(r.weights.size == 2)
  }

  it should "be ale to reduce a coefficient" in {
    val m = new MutateOTron()
    val w:Weighting = List(1.0)
    val r = m.reduce(w)
    assert(r.weights.isEmpty)
  }

  it should "reduce should not modify an empty list" in {
    val m = new MutateOTron()
    val w:Weighting = List()
    val r = m.reduce(w)
    assert(r.weights.isEmpty)
  }

  it should "be able to mutate weightings" in {
    val m = new MutateOTron(extend_prob=0.05, reduce_prob=0.05, tweak_prob=0.05, jumble_prob=0.05, flip_prob=0.05)
    val ws:Weightings = weightings(Map("A"->List(1.0, 1.0, 1.0), "B"->List(0.0, 0.0, 0.0)))
    val rs = m.mutate(ws)
    assert(ws.weightings.keys==rs.weightings.keys)
  }

}
