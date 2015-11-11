package controllers

import com.friendly.umbrella.{Code, TownForecast, Weather}
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def main = Action {
    Ok(views.html.umbrella(Seq[(String, String, String)](
      ("GET", "/weather/:code", "GET WEATHER BY CODE"),
      ("GET", "/gcm/register", "GCM REGISTER. WILL GONNA DEPRECATED"),
      ("POST", "/gcm/register", "GCM REGISTER"),
      ("GET", "/gcm/send/:regId/:msg", "GCM SEND MESSAGES"),
      ("POST", "/gcm/send/:regId/:msg", "GCM SEND MESSAGES")
    )))
  }

  def test = Action {
    Ok(Weather.showAllRegions)
  }

  def getTownWeather = Action {
    Ok(Weather.getTownWeather(Code("2611051000", "중앙동"))).as("text/xml")
  }

  def getVersion = Action {
    Ok("201510251233")
  }

  val PRETTY_PRINT_INDENT_FACTOR : Int = 4
  def getTownWeatherJson = Action {
    Ok(org.json.XML.toJSONObject(Weather.getTownWeather(Code("2611051000", "중앙동"))).toString(PRETTY_PRINT_INDENT_FACTOR)).as("application/json")
  }

  def getTownWeatherJson1(code : String) = Action {
    //check valid code
    val town = TownForecast(Code(code, ""))
    Ok("["+town.x + ", " + town.y+"] " + town.category + " "+ town.willRain + " " + town.maxPop)
  }

  def weatherTest(): Unit = {
    Ok("hi")
  }
}
object CodePool {
  //todo should change
  //lazy val codeStr = Source.fromFile(new File("/Users/YeonjuMac/Desktop/locationCode.tsv"))
//  lazy val codes = codeStr.getLines().map{x=>
//    val xs = x.split("\t")
//
//    Code(xs(0), xs(1))
//  }
}
case class Location(x : Double, y : Double)
class WeatherMap {
//  val map : immutable.ParHashMap[Location, Code] = {
//    CodePool.codes.map{x=>
//      Weather.getTownWeather(x)
//    }
//  }
}