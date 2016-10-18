package empathy

/**
  * Created by salim on 18/10/2016.
  */
class MutateOTron {

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
