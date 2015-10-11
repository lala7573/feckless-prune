package controllers

import com.friendly.umbrella.{Code, Weather}
import play.api.mvc._

//import scala.slick.model.codegen.SourceCodeGenerator

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

  def testgcm = Action {
   // Ok(GCMService.send())
    Ok("no operation")
  }

//  def codegen = Action {
//    SourceCodeGenerator.main(
//      Array(
//        "scala.slick.driver.MySQLDriver",
//        "com.mysql.jdbc.Driver",
//        "jdbc:mysql://127.0.0.1:3306/friendly?characterEncoding=UTF-8&useUnicode=true" ,
//        "tmp/",
//        "codegen/",
//        "root",
//        ""
//      )
//    )
//    Ok("code gen")
//  }
}