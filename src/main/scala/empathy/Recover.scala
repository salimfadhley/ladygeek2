import empathy.{Config, Evolver, Main, RankingFitness, SourceData}
import empathy.SourceData._
import empathy.scoring.MegaScorer
import empathy.weight.{MutateOTron, Weightings}

import scala.collection.immutable.Map

object Recover extends App {

  val input_filename = "/runs/d_1477589581_-4.606_.json"
  val w = Weightings.fromJsonFile(input_filename)

  val e = new Evolver[MixedData, Weightings]
  val sd = new SourceData
  val m = new MutateOTron()
  val s:MegaScorer = sd.targets
  def ff = new RankingFitness(s.score)
  val columnNames:List[String] = sd.getColumns


  val fitness_and_ranking = ff.calculateFitnessAndRanking(w, sd.mixed_data)

  val fitness = fitness_and_ranking._1
  val ranking = fitness_and_ranking._2

  Main.writeRanking("x_recalc.txt", ranking)

  val explaination = ff.explainRows(sd.mixed_data, w, Config.scoreGrouping)

  println(explaination)

  println(w)

}