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
    assert(ms.scorers.head.strategy=="Keep")
    assert(ms.scorers.tail.head.strategy=="Keep")
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

  it should "be creatable from a list of (name, strategy)" in {
    val ms:MegaScorer = List(
      ("A", "Keep"),
      ("B", "Top"),
      ("C", "Lower"),
      ("D", "Raise"),
      ("E", "Bottom")
    )
    assert(ms.scorers.head.strategy=="Keep")
    assert(ms.scorers.tail.head.strategy=="Keep")
  }

  it should "give a score of zero when empty" in {
    val ms:MegaScorer = new MegaScorer(Nil)
    val ind:List[String] = List("A", "B", "C", "D")
    assert(ms.score(ind)==0)
  }

  it should "give a non-zero score if an item is displaced" in {
    val index = List("A", "B", "C", "D")
    val ms:MegaScorer = List(("A", "Top"), ("C", "Bottom"))

    assert (ms.score(index) == 0)
  }






}
