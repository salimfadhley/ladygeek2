import empathy.Weightings

object Recover extends App {
  val w = Weightings.fromJsonFile("/runs/x.json")

  println(w)

}