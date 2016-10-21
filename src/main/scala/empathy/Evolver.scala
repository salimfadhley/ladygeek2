package empathy

/**
  * Created by salim on 21/10/2016.
  */
class Evolver[D,W] {

  type Result = (Double, W)

  def evolve(data:D, populationSize:Int, initialWeightings: W, mutatorFunction:(W)=>W, fitnessFunction:(W,D)=>Double ):Result = {
    val results = (0 to populationSize)
      .map((i) =>{
        val w = mutatorFunction(initialWeightings)
        (fitnessFunction(w, data), w)
    }).toMap
    val maxScore = results.keysIterator.max
    val result: (Double, W) = (maxScore, results(maxScore))
    result
  }

  def multiEvolve(trials:Int, data:D, populationSize:Int, initialWeightings: W, mutatorFunction:(W)=>W, fitnessFunction:(W,D)=>Double):Result = {
    var weightings:W = initialWeightings
    var bestScore:Double = Double.MinValue

    (0 to trials).foreach((x)=> {
      println(s"Trial $x")
      val trialReslt: Result = evolve(data, populationSize, weightings, mutatorFunction, fitnessFunction)

      if (trialReslt._1 > bestScore) {
        bestScore = trialReslt._1
        weightings = trialReslt._2
        println(s"Best score ${bestScore}")
      }
    })
    (bestScore, weightings)
  }

}
