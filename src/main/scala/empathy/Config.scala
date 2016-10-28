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

  def weighting_filename(score:Double) = {
    val sc:String = f"$score%1.3f"
    s"d_${timestamp}_${sc}_.json"
  }

  def ranking_filename(score:Double) = {
    val sc:String = f"$score%1.3f"
    s"d_${timestamp}_${sc}_.txt"
  }

}
