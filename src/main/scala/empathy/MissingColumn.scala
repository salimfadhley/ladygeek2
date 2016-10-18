package empathy

/**
  * Created by salim on 18/10/2016.
  */
case class MissingColumn(s: String) extends RuntimeException(s) { }
