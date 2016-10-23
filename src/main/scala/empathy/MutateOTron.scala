package empathy

import scala.collection.immutable.IndexedSeq

/**
  * Created by salim on 18/10/2016.
  */
class MutateOTron(extend_prob:Double=0.05, reduce_prob:Double=0.05, tweak_prob:Double=0.05, jumble_prob:Double=0.05, flip_prob:Double=0.05, tweak_range:Double=1.0) {

  def mutate(ws:Weightings, fuzz_multiplier:Double=1.0):Weightings = {
    val probs = List(extend_prob, reduce_prob, tweak_prob, jumble_prob,flip_prob)
    val cumulative_probs = probs.indices.map((i)=>{probs.slice(0, 1 + i).sum})
    val fuzz:Double = tweak_range * fuzz_multiplier
    ws.weightings.map{
      case (n:String, w:Weighting) => mutateSingleWeighting(cumulative_probs,n, w, fuzz)
    }
  }

  def mutateSingleWeighting(cumulative_probs: IndexedSeq[Double], n: String, w: Weighting, fuzz:Double): (String, Weighting) = {
    val r: Double = util.Random.nextDouble()
    val new_weightings = r match {
      case r if r < cumulative_probs(0) => extend(w, fuzz)
      case r if r < cumulative_probs(1) => reduce(w)
      case r if r < cumulative_probs(2) => tweak(w, fuzz)
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
    w match {
      case w if w.weights.nonEmpty => flipIndex(w, scala.util.Random.nextInt(w.weights.length))
      case w => w
    }
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
    w.weights match {
      case w if w.nonEmpty => val index = util.Random.nextInt(w.weights.size)
        w.weights.zipWithIndex.map{
          case x if x._2 == index => tweak(x._1, tweak_range)
          case x => x._1
        }
      case w => w
    }
  }

}
