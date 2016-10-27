import empathy.weight.{MutateOTron, Weighting, Weightings}

val w = Weightings.make(List("A", "B", "C", "D", "E", "F"))
val m = new MutateOTron

val w2 = m.mutate(w)

w2.weightings.foreach((tuple: (String, Weighting)) =>{
  println(tuple)
})