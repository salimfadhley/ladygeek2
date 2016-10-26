package empathy

import java.io.{BufferedWriter, FileWriter}

import empathy.scoring.MegaScorer
import play.api.libs.json.Json

import scala.reflect.io.File

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




  val result: (Double, Weightings) = e.multiEvolve( 50, sd.mixed_data, 50, ws, m.mutate, ff.calculateFitness)

  val final_ranking = ff.calculateRanking(result._2, sd.mixed_data)
  final_ranking.zipWithIndex.map{case (s: String, i: Int) =>{
    println(s"$i. -> $s")
  }}

  val output_path = File("C:/Users/salim/workspace/ladygeek2/src/main/resources/runs/x.json")
  val writer = new FileWriter("C:/Users/salim/workspace/ladygeek2/src/main/resources/runs/x.json")
  writer.write(Json.prettyPrint(result._2.toJson))
  writer.close()

}
