import java.util.Calendar

import play.api.libs.json.{JsArray, JsObject, JsValue, Json}

val foo = Json.parse("""[{"a":3,"b":9},{"a":3,"b":3},{"a":2,"b":9},{"a":3,"b":9}]""").as[JsArray]

foo.value.map((v: JsValue) =>v.as[JsObject]).map{
  case jo: JsObject => {
    val jom = jo.value
    (jom("a"), jom("b"))
  }
}.toMap