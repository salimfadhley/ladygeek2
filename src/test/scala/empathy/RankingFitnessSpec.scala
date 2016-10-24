package empathy

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by salim on 21/10/2016.
  */
class RankingFitnessSpec extends FlatSpec with Matchers {

  implicit def foo(x:Map[String,Int]):Map[String,Double] = {
    val m = x.valuesIterator.max.toDouble
    x.map{case (k:String, v:Int) => (k,v.toDouble / m)}
  }

//  "RankingFitness" should "be able to give an index a score of zero if it perfectly aligns" in {
//    val targets = Map(
//      "A"->0,
//      "B"->1,
//      "C"->2
//    )
//    val ranking = List("A", "B", "C")
//    val rf = new RankingFitness(targets)
//    val rankScore:Double = rf.scoreRanking(ranking)
//    assert(rankScore == 0)
//  }

  it should "be able to give an index a non-zero if it does not align" in {
    val targets = Map(
      "A"->0,
      "B"->1,
      "C"->2
    )

    val rf = new RankingFitness(targets)
    assert(rf.scoreRanking(List("X", "A", "B", "C")) < 0)
  }

  it should "be able to give a score relative to distance from expected point squared" in {
    val targets = Map(
      "A"->0,
      "B"->1,
      "C"->2
    )
    val rf = new RankingFitness(targets)

    assert(rf.scoreRanking(List("A", "B", "X", "Y", "C", "Z")) < rf.scoreRanking(List("A", "B", "X", "Y", "Z", "C")))
  }



}
