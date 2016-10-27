package empathy

import java.io.{BufferedWriter, FileWriter}

import empathy.SourceData.MixedData
import empathy.scoring.MegaScorer
import empathy.weight.{MutateOTron, Weightings}
import play.api.libs.json.Json

import scala.reflect.io.File

/**
  * Created by sal on 17/10/16.
  */
object Main extends App {
  val e = new Evolver[MixedData, Weightings]
  val sd = new SourceData
  val m = new MutateOTron()
  val s:MegaScorer = sd.targets
  def ff = new RankingFitness(s.score)
  val columnNames:List[String] = sd.getColumns

  while (true) {
    calculateIndex()
  }

  def calculateIndex(): Unit = {
    val ws = Weightings.make(columnNames, default = 0.0, fuzz = 2.0)
    val result: (Double, Weightings) = e.multiEvolve(Config.trials, sd.mixed_data, Config.population, ws, m.mutate, ff.calculateFitness)
    val fitness_and_ranking = ff.calculateFitnessAndRanking(result._2, sd.mixed_data)

    val json_filename = Config.weighting_filename(fitness_and_ranking._1)
    val json_writer = new FileWriter(s"${Config.runDir}/${json_filename}")

    val ranking_filename = Config.ranking_filename(fitness_and_ranking._1)

    val ranking = fitness_and_ranking._2

    json_writer.write(Json.prettyPrint(result._2.toJson))
    json_writer.close()

    writeRanking(ranking_filename, ranking)
  }


  def writeRanking(ranking_filename: String, ranking: List[String]): Unit = {
    val ranking_writer = new FileWriter(s"${Config.runDir}/$ranking_filename")
    ranking.zipWithIndex.foreach { case (s: String, i: Int) => {
      val q = "\""
      val message: String = s"""$i,$q$s$q"""
      println(message)
      ranking_writer.write(message + "\n")
    }
    }

    ranking_writer.close()
  }
}
