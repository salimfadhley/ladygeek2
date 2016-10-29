package empathy

import empathy.SourceData.MixedData
import empathy.weight.Weightings

import scala.collection.immutable.Map

/**
  * Created by salim on 21/10/2016.
  */
class RankingFitness(scoringFunction:(List[String])=>Double) {
  def explainRows(row: Map[String,Double], weightings: Weightings, explainMap:Map[String,Set[String]]): Map[String,Double] = {
    val columnScores:Map[String,Double] = weightings.calculateColumnScores(row)
    explainMap.map{
      case (groupName: String, columnNames: Set[String]) => {
        (groupName, columnNames.map(columnScores(_)).sum)
      }
    }
  }


  def scoreRow(row: Map[String,Double], weightings: Weightings): Double = weightings.calculateScore(row)

  def scoreRows(rows:SourceData.MixedData, weightings: Weightings):List[ScoringResult] = {
    rows.map{
      case (name, row) => ScoringResult(name,weightings.calculateScore(row))
    }.toList
  }



  def calculateFitness(weightings: Weightings, data: MixedData): Double = {
    calculateFitnessAndRanking(weightings,data)._1
  }

  def calculateFitnessAndRanking(weightings: Weightings, data: MixedData): (Double, List[String]) = {
    val ranking: List[String] = calculateRanking(weightings, data)
    val fitness = scoringFunction(ranking) + weightings.fitness
    (fitness, ranking)
  }

  def calculateRanking(weightings: Weightings, data: MixedData): List[String] = {
    val score: List[ScoringResult] = scoreRows(data, weightings)
    score.sortWith((lt,rt)=>lt.score < rt.score).map(_.name)
  }
}