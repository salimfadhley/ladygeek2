package empathy

/**
  * Created by salim on 19/10/2016.
  */
trait Fitness {

  def apply(scoring:Map[String,Double], weightings: Weightings):Double

}
