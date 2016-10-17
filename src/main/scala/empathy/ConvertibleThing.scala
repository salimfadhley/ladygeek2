package empathy

import java.lang.RuntimeException

/**
  * Created by salim on 19/09/2016.
  */
case class ConvertibleThing(_s: String) {

  implicit def i: Int = _s.toInt

  def oi: Option[Int] = Option(i)

  implicit def b: Boolean = _s.toInt > 0

  implicit def s: String = _s

  def d: Double = {
    try {
      _s.toDouble
    } catch {
      case x:NumberFormatException => throw new RuntimeException(s"Cannot convert ${_s} to Double")
    }

  }

  def od: Option[Double] = Option(d)

}

object ConvertibleThing {

  def unapply(s: String): ConvertibleThing = {
    ConvertibleThing(s)
  }

}