package empathy

import scala.collection.immutable.IndexedSeq

/**
  * Created by salim on 18/10/2016.
  */
class MutateOTron(extend_prob:Double=0.05, reduce_prob:Double=0.05, tweak_prob:Double=0.05, jumble_prob:Double=0.05, flip_prob:Double=0.05, tweak_range:Double=1.0) {

  def mutate(ws:Weightings):Weightings = {
    val probs = List(extend_prob, reduce_prob, tweak_prob, jumble_prob,flip_prob)
    val cumulative_probs = probs.indices.map(probs.slice(0, _).sum)

    ws.weightings.map((x: (String, Weighting)) => {
      mutateSingleWeighting(cumulative_probs, x._1, x._2)
    })
  }

  def mutateSingleWeighting(cumulative_probs: IndexedSeq[Double], n: String, w: Weighting): (String, Weighting) = {
    val r: Double = util.Random.nextDouble()
    val new_weightings = r match {
      case r if r < cumulative_probs(0) => extend(w, tweak_range)
      case r if r < cumulative_probs(1) => reduce(w)
      case r if r < cumulative_probs(2) => tweak(w, tweak_range)
      case r if r < cumulative_probs(3) => jumble(w)
      case r if r < cumulative_probs(4) => flipACoefficient(w)
      case _ => w
    }
    (n, new_weightings)
  }

  def reduce(w: Weighting):Weighting = {
    w.weights.dropRight(1)
  }

  def extend(w: Weighting, tweak_range:Double):Weighting = {
    w.weights :+ tweak(0.0, tweak_range)
  }

  def flipACoefficient(w:Weighting):Weighting = {
    val r = scala.util.Random.nextInt(w.weights.length)
    flipIndex(w, r)
  }


  def flipIndex(w: Weighting, index: Int): Weighting = {
    w.weights.zipWithIndex.map((x: (Double, Int)) => x._2 match {
      case ind if ind == index => x._1 * -1
      case ind => x._1
    })
  }

  def jumble(w:Weighting):Weighting = {
    util.Random.shuffle(w.weights)
  }

  def tweak(input: Double, tweak_range: Double):Double = {
    input + (util.Random.nextDouble() - 0.5) * tweak_range
  }

  def tweak(w: Weighting, tweak_range:Double=1.0):Weighting = {
    val index = util.Random.nextInt(w.weights.size)
    w.weights.zipWithIndex.map{
      case x if x._2 == index => tweak(x._1, tweak_range)
      case x => x._1
    }
  }

}
