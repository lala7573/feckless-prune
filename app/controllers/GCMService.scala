package controllers

import com.google.android.gcm.server.{Message, Result, Sender}
import controllers.ResponseService._
import models.DBTables._
import play.api.Logger
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import play.api.mvc.{Action, Controller}
import play.api.Play.current

object GCMService extends Controller {

  val GCM_SERVER : String = "gcm.googleapis.com"
  val GCM_PORT : Int = 5235

  val TEST_SERVER : String = "gcm-preprod.googleapis.com"
  val TEST_PORT : Int = 5236

  val GCM_ELEMENT_NAME : String = "gcm"
  val GCM_NAMESPACE : String = "google:mobile:data"

  val PROJECT_ID : String = "147568965374"
  val API_KEY = "AIzaSyCvhmsezjny6VOZ6YSgqF2MTrzkzFlhcHg"
  val numOfRetries = 3

  //test
  def send(regId : String, message : String) = Action {
//    if(message.length > 4096)
//      Ok(invalid("message is too long"))

    val sender : Sender = new Sender(API_KEY)
    val msg : Message = new Message.Builder().addData("message", message).build()

    val result : Result = sender.send(msg, regId, numOfRetries)
    println(regId)
    Ok(success(regId + "\t" + result.getErrorCodeName +"\t" + result.getCanonicalRegistrationId + "\t" + result.getMessageId))
  }


  def registerTest(name : String, regId : String) = Action {
    addUser(name, regId)
    Ok(success(regId + " is registered"))
  }

  def register = Action(parse.json) { request =>
    val name = (request.body \ "name").asOpt[String]
    val regId = (request.body \ "regId").asOpt[String]

    if(name.isEmpty || regId.isEmpty)
      Ok(invalid("invalid parameters"))

    addUser(name.get, regId.get)
    Ok(success(regId + " is registered"))
  }

  def addUser(name : String, regId : String) : Int = DB.withSession { implicit session =>
    Logger.logger.info(name + "/" + regId + "is registered")
    (Users returning Users.map(_.userId)) += UsersRow(0, Some(name), Some(regId))
  }

}