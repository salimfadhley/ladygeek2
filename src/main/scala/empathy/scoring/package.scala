package empathy

/**
  * Created by salim on 24/10/2016.
  */
package object scoring {

  type ScorerSpec = List[(String, String, Double)]

  type ScorerSpecLite = List[(String, String)]

  implicit def ms(scorers:List[Scorer]):MegaScorer = {
    MegaScorer(scorers=scorers)
  }


  implicit def ms_from_spec(spec:ScorerSpec) = {
    MegaScorer(spec.map{
      case (company, "Keep", target) => new KeepScorer(company, target)
      case (company, "Top", _) => new KeepScorer(company, 0,0)
      case (company, "Bottom", _) => new KeepScorer(company, 1.0)
      case (company, "Raise", target) => new RaiseScorer(company, target)
      case (company, "Lower", target) => new LowerScorer(company, target)
      case (_, x, _) => throw new RuntimeException(s"Bad Strategy: $x")
    })
  }

  def specFromSpecLite(specLite: ScorerSpecLite): ScorerSpec = {
    val length = specLite.size
    specLite.zipWithIndex.map{
      case ((name:String, strategy:String), index:Int) =>
        (name, strategy, index.toDouble / (length - 1).toDouble)
    }
  }

  implicit def ms_from_strategy_and_name_list(specLite:ScorerSpecLite):MegaScorer = {
    ms_from_spec(specFromSpecLite(specLite))
  }

}
