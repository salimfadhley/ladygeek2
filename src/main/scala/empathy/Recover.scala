import empathy.weight.Weightings

object Recover extends App {
  val w = Weightings.fromJsonFile("/runs/x.json")

  println(w)

}