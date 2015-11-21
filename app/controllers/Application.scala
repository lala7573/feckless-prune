package controllers

import com.friendly.umbrella.{Code, Location, TownForecast, Weather}
import controllers.ResponseService._
import play.api.libs.json.Json
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


  def getTownWeather = Action {
    Ok(Weather.getTownWeather(Code("2611051000", "중앙동"))).as("text/xml")
  }

  def getVersion = Action {
    Ok("201510251233")
  }

  def getLocationCode = Action(parse.json) { request =>
    val fullRegionName = (request.body \ "region").asOpt[String]

    if(fullRegionName.isEmpty)
      Ok(invalid("invalid parameter"))

    val location = Location.find(fullRegionName.get)

    Ok(success(Json.obj("region"->fullRegionName, "code"->location.code)))
  }


  def getTownWeatherJson(code : String) = Action {
    //check valid code
    val town = TownForecast(Code(code, ""))
    Ok("["+town.x + ", " + town.y+"] " + town.category + " "+ town.willRain + " " + town.maxPop)
  }


  /*test*/
  def weatherTest(): Unit = {
    Ok("hi")
  }

  val PRETTY_PRINT_INDENT_FACTOR : Int = 4
  def test = Action {
    Ok(org.json.XML.toJSONObject(Weather.getTownWeather(Code("2611051000", "중앙동"))).toString(PRETTY_PRINT_INDENT_FACTOR)).as("application/json")
  }

  def test2 = Action {
    Ok(Weather.showAllRegions)
  }

}