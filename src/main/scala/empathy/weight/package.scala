package empathy

/**
  * Created by salim on 27/10/2016.
  */
package object weight {

  implicit def weighting(weights:List[Double]):Weighting = {
    Weighting(weights)
  }

  implicit def weightings(weightings:Map[String,Weighting]):Weightings = {
    Weightings(weightings)
  }

  implicit def weightings2(w:Map[String,List[Double]]):Weightings = {
    Weightings(w.map{case (s: String, doubles: List[Double]) =>{
      (s, weighting(doubles))
    }})

  }

}
