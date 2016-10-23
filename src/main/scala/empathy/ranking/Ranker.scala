package empathy.ranking

/**
  * Created by salim on 23/10/2016.
  */
trait Ranker {
  val strategy:String
  def rank(input:List[String]):Double
}
