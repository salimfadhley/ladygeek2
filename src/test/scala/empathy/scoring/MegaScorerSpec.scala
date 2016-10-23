package empathy.scoring

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 24/10/2016.
  */
class MegaScorerSpec extends FlatSpec with Matchers {

  "MegaScorer" should "be creatable from a list of scorers" in {
    val ms:MegaScorer = List(
      new KeepScorer("A", 0),
      new KeepScorer("Z", 1)
    )
    assert(ms.scorers.head.strategy=="keep")
    assert(ms.scorers.tail.head.strategy=="keep")
  }

  it should "be creatable from a list of (name, strategy, target)" in {
    val ms:MegaScorer = List(
      ("A", "Keep", 0.5),
      ("B", "Top", 0.0),
      ("C", "Lower", 0.75),
      ("D", "Raise", 0.75),
      ("E", "Bottom", 0.1)
    )

    assert(ms.scorers.head.strategy=="Keep")
    assert(ms.scorers.tail.head.strategy=="Keep")

  }




}
