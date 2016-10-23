package empathy

import empathy.SourceData.MixedData
import scala.collection.immutable.Map

/**
  * Created by salim on 21/10/2016.
  */
class RankingFitness(targets:Map[String,Double]) {

  def scoreRanking(ranking:List[String]):Double = {
    targets.map{
      case (name:String, t:Double) => Math.pow((ranking.indexOf(name).toDouble/ranking.size - t), 2)
  }.sum * -1

  }

  def calculateRanks(score: Map[String, Double]): List[String] = {
    val inverted = score.map(_.swap)
    inverted.keys.toList.sortWith(_ < _).map(inverted(_))
  }

  def calculateFitness(weightings: Weightings, data: MixedData): Double = {
    val score:EmpathyScoring.Result = EmpathyScoring.scoreRows(data, weightings)
    val ranking:List[String] = calculateRanks(score)
    scoreRanking(ranking)
  }

}


object RankingFitness {
  def makeFromIndex(targets: Map[String, Int], indexSize: Int) = {
    new RankingFitness(targets.map{ case (name, pos) => (name, pos.toDouble / indexSize)})
  }

}