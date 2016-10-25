package empathy

import empathy.scoring.MegaScorer

/**
  * Created by sal on 17/10/16.
  */
object Main extends App {
  val e = new Evolver[SourceData.MixedData, Weightings]
  val sd = new SourceData
  val m = new MutateOTron()
  val s:MegaScorer = sd.targets
  def ff = new RankingFitness(s.score)
  val columnNames:List[String] = sd.getColumns
  val ws = Weightings.make(columnNames, default = 1.0)

  val result: (Double, Weightings) = e.multiEvolve( 100, sd.mixed_data, 2000, ws, m.mutate, ff.calculateFitness)

  val final_ranking = ff.calculateRanking(result._2, sd.mixed_data)
  final_ranking.zipWithIndex.map{case (s: String, i: Int) =>{
    println(s"$i. -> $s")
  }}
}
