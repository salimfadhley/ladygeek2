package empathy

import empathy.SourceData.MixedData
import scala.collection.immutable.Map

/**
  * Created by salim on 21/10/2016.
  */
class RankingFitness(scoringFunction:(List[String])=>Double) {



  type Result = List[(Double, String)]

  def scoreRow(row: Map[String,Double], weightings: Weightings): Double = weightings.calculateScore(row)

  def scoreRows(rows:SourceData.MixedData, weightings: Weightings):Result = {
    rows.map{
      case (name, row) => (weightings.calculateScore(row), name)
    }.toList
  }

  def calculateFitness(weightings: Weightings, data: MixedData): Double = {
    val ranking: List[String] = calculateRanking(weightings, data)
    scoringFunction(ranking)
  }

  def calculateRanking(weightings: Weightings, data: MixedData): List[String] = {
    val score = scoreRows(data, weightings)
    score.sortWith((lt,rt)=>lt._1 < rt._1).map(_._2)
  }
}