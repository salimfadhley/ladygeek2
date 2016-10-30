package empathy

/**
  * Created by salim on 26/10/2016.
  */
object Config {
  val main_input_data: String = "/inputs/ygl_corrected_no_china.csv"
  val target_file: String = "/inputs/targets.csv"
  val population: Int = 3000
  val trials: Int = 100
  val resourceDir = "C:/Users/salim/workspace/ladygeek2/src/main/resources/"
  val runDir:String = "C:/Users/salim/workspace/ladygeek2/src/main/resources/runs"
  val timestamp: Long = System.currentTimeMillis / 1000

  val scoreGrouping:Map[String,Set[String]] = Map(
    "Survey"->Set("ygl_skips","ygl_answers", "ygl_average"),
    "Social"->Set("i_we_you", "empathic_words", "repetition", "half_hour_minute_response_rate", "stock_phrases", "five hour response rate", "ten_minute_response_rate"),
    "EthicalEnvironmental"->Set("proportion_women", "major_scandal", "carbon_sector_score", "carbon_country_score"),
    "CorporateCulture"->Set("glassdoor_comp","glassdoor_opportunity", "glassdoor_values", "glassdoor_friend", "glassdoor_balance"),
    "CorporateLeadership"->Set("ceo_vs_market_cap", "glassdoor_ceo", "glassdoor_leadership")
  )

  def weighting_filename(score:Double) = {
    val sc:String = f"$score%1.3f"
    s"d_${timestamp}_${sc}_.json"
  }

  def ranking_filename(score:Double) = {
    val sc:String = f"$score%1.3f"
    s"d_${timestamp}_${sc}_.txt"
  }

  def explainFilename = s"${resourceDir}explain.csv"

}
