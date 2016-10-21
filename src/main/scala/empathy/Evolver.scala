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
    (maxScore, results(maxScore))
  }

  def multiEvolve(trials:Int, data:D, populationSize:Int, initialWeightings: W, mutatorFunction:(W)=>W, fitnessFunction:(W,D)=>Double):Result = {
    val results = (0 to trials).map((x)=>
      evolve(data, populationSize, initialWeightings, mutatorFunction, fitnessFunction)
    ).toMap

    val maxScore = results.keysIterator.max
    val bestWeighting = results(maxScore)

    (maxScore, bestWeighting)
  }

}
