package controllers

import com.friendly.umbrella.{Code, Weather}
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test = Action {
    Ok(Weather.showAllRegions)
  }

  def getTownWeather = Action {
    Ok(Weather.getTownWeather(Code("2611051000", "중앙동"))).as("text/xml")
  }

}