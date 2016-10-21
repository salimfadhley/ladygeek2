package empathy

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
    val w:Weightings = Weightings.make(columns=List("A", "B", "C"), default = 2.0, coefficents = 2, fuzz = 0.0)
    val res:Double = EmpathyScoring.scoreRow(row=row, weightings=w)
    assert(res==18.0)
  }

  it should "be able to calculate the score for rows" in {
    val row:Map[String,Double] = Map(
      "A"->1.0,
      "B"->2.0,
      "C"->3.0
    )
    val rows = Map("x"->row)
    val w:Weightings = Weightings.make(columns=List("A", "B", "C"), default = 2.0, coefficents = 2, fuzz = 0.0)
    val res:Map[String,Double] = EmpathyScoring.scoreRows(rows=rows, weightings=w)
    assert(res==Map("x"->18.0))
  }




}
