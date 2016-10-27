package empathy

/**
  * Created by salim on 21/10/2016.
  */
class Evolver[D,W] {

  type Result = (Double, W)

  def evolve(data:D, populationSize:Int, initialWeightings: W, mutatorFunction:(W, Double)=>W, fitnessFunction:(W,D)=>Double, fuzz_multiplier:Double ):Result = {
    val results = (0 to populationSize)
      .par
      .map((i) =>{
        val w = mutatorFunction(initialWeightings, fuzz_multiplier)
        (fitnessFunction(w, data), w)
    }).toMap
    val maxScore = results.keysIterator.max
    val result: (Double, W) = (maxScore, results(maxScore))
    result
  }

  def multiEvolve(trials:Int, data:D, populationSize:Int, initialWeightings: W, mutatorFunction:(W, Double)=>W, fitnessFunction:(W,D)=>Double, populationMultiplier:Double=1.5):Result = {
    var weightings:W = initialWeightings
    var bestScore:Double = Double.MinValue
    var fuzz_multiplier:Double = 1.0
    var pop:Int = populationSize

    (0 to trials).foreach((x)=> {
      println(s"Trial $x")
      val trialReslt: Result = evolve(data, populationSize, weightings, mutatorFunction, fitnessFunction, fuzz_multiplier)

      if (trialReslt._1 > bestScore) {
        bestScore = trialReslt._1
        weightings = trialReslt._2
        fuzz_multiplier = 1.0
        pop = (pop * populationMultiplier).toInt
        println(s"Best score $bestScore, population: ${pop}, fuzz multiplier: ${fuzz_multiplier}")
      } else {
        fuzz_multiplier = fuzz_multiplier * 1.1
        pop = populationSize
      }
    })
    (bestScore, weightings)
  }

}
