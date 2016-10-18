package empathy

/**
  * Created by sal on 17/10/16.
  */
case class Weighting(weights:List[Double]) {

  def apply(input:Double):Double = {
    weights.zip(Stream.from(0)).map(p=>Math.pow(input, p._2)*p._1).sum
  }


}