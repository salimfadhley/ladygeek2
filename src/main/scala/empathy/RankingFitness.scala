package empathy

import empathy.SourceData.MixedData
import empathy.weight.Weightings
import scala.collection.immutable.Map

/**
  * Created by salim on 21/10/2016.
  */
class RankingFitness(scoringFunction:(List[String])=>Double) {

  type ExplainMap = Map[String,Set[String]]
  type RowExplaination = Map[String,Double]
  type RowsExplaination = Map[String,RowExplaination]


  def explainRow(row: Map[String,Double], weightings: Weightings, explainMap:ExplainMap): RowExplaination = {
    val columnScores:Map[String,Double] = weightings.calculateColumnScores(row)
    explainMap.map{
      case (groupName: String, columnNames: Set[String]) => {
        (groupName, columnNames.map(columnScores(_)).sum)
      }
    }
  }

  def explainRows(rows:MixedData, weightings: Weightings, explainMap:ExplainMap): RowsExplaination = {

    rows.map {
      case (s: String, row: Map[String, Double]) => {
        (s, explainRow(row, weightings, explainMap))
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