package empathy

import empathy.SourceData.MixedData
import scala.collection.immutable.Map

/**
  * Created by salim on 21/10/2016.
  */
class RankingFitness {

  def calculateRanks(score: Map[String, Double]): List[String] = {
    val inverted = score.map(_.swap)
    inverted.keys.toList.sortWith(_ < _).map(inverted(_))
  }

  def calculateFitness(weightings: Weightings, data: MixedData): Double = {
    val score:EmpathyScoring.Result = EmpathyScoring.scoreRows(data, weightings)
    val ranking:List[String] = calculateRanks(score)

    0.0
  }

}
