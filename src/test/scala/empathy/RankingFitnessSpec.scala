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

}
