package empathy

/**
  * Created by sal on 17/10/16.
  */
object Main extends App {

  val e = new Evolver[SourceData.MixedData, Weightings]
  val sd = new SourceData
  val m = new MutateOTron()
  val f = new RankingFitness()

  val columnNames:List[String] = sd.getColumns()


  val ws = Weightings.make(columnNames)

  val result = e.multiEvolve( 100, sd.mixed_data, 100, ws, m.mutate, f.calculateFitness)


}
