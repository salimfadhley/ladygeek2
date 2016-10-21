package empathy

/**
  * Created by sal on 17/10/16.
  */
object Main extends App {

  val targets = Map(
    "Alphabet Inc" -> 16,
    "Aviva PLC" -> 87,
    "Burberry Group PLC" -> 76,
    "Chevron Corporation"-> 82
  )

  val e = new Evolver[SourceData.MixedData, Weightings]
  val sd = new SourceData
  val m = new MutateOTron()
  val f = new RankingFitness(targets)

  val columnNames:List[String] = sd.getColumns()


  val ws = Weightings.make(columnNames, default = 1.0)

  val result = e.multiEvolve( 100, sd.mixed_data, 100, ws, m.mutate, f.calculateFitness)

  println(result)
}
