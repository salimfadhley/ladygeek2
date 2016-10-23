package empathy.scoring

/**
  * Created by salim on 23/10/2016.
  */
trait Scorer {
  val strategy:String
  def rank(input:List[String]):Double
}
