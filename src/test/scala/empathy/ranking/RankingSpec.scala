package empathy.ranking

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 23/10/2016.
  */
class RankingSpec extends FlatSpec with Matchers {

  "KeepRanking" should "score zero when items are in the correct position" in {
    val kr = new KeepRanker("B", 0.5)
    val ranking = List("A", "B", "C")
    assert(kr.rank(ranking)==0.0)
  }

  it should "throw an error if the thing er are trying to score is not in the ranking" in {
    val kr = new KeepRanker("X", 0.5)
    val ranking = List("A", "B", "C")
    intercept[ItemNotInRanking]{kr.rank(ranking)}
  }

  it should "give a score proportional to the distance squared" in {
    val kr = new KeepRanker("X", 0.0)
    val ranking = List("A", "X", "C")
    assert(kr.rank(ranking)==0.25)
  }

  "RaiseRanker" should "give a zero score when the score has improved" in {
    val rr = new RaiseRanker("X", 1.0)
    val ranking = List("X", "A", "B", "C")
    assert(rr.rank(ranking)==0.0)
  }

  it should "be consistent with KeepRanker if the score is lower" in {
    val rr = new RaiseRanker("X", 0.0)
    val ranking = List("A", "B", "C", "X")
    assert(rr.rank(ranking)==1.0)
  }

  "LowerRanker" should "give a zero score when the score has reduced" in {
    val rr = new LowerRanker("X", 0.0)
    val ranking = List("A", "B", "C", "X")
    assert(rr.rank(ranking)==0.0)
  }

  it should "be consistent with KeepRanker if the score is higher" in {
    val rr = new LowerRanker("X", 1.0)
    val ranking = List("X", "A", "B", "C")
    assert(rr.rank(ranking)==1.0)
  }





}
