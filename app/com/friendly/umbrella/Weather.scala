package com.friendly.umbrella

import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.ws.WS

import scala.concurrent.Await
import scala.concurrent.duration._

case class Code(code : String, value : String) {
  var subList : List[Code] = List[Code]()
}
/*
case class TopCode(topCode : Int, topValue : String, midList : List[MidCode]) extends Code(topCode, topValue) {


}
case class MidCode(midCode : Int, midValue : String, leafList : List[LeafCode]) extends Code(midCode, midValue) {
  def leafUrl(midCode: MidCode): String = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.%d.json.txt".format(midCode.midCode)
}
case class LeafCode(leafCode : Int, leafValue : String) extends Code(leafCode, leafValue)
*/

object Weather {
  var topCodes = List[Code]()
  def midUrl(code: String): String = "http://www.kma.go.kr/DFSROOT/POINT/DATA/mdl.%s.json.txt".format(code)
  def leafUrl(code : String): String = "http://www.kma.go.kr/DFSROOT/POINT/DATA/leaf.%s.json.txt".format(code)

  val topUrl = "http://www.kma.go.kr/DFSROOT/POINT/DATA/top.json.txt"

  implicit def codeReads : Reads[Code] = {
    ((__ \ "code").read[String] and
      (__ \ "value").read[String]
      )(Code)
  }

  def crawl : Unit = {
    if(topCodes.isEmpty) {
      topCodes = getCodes(crawl(topUrl))
      topCodes.flatMap(getSubCodesFromTopCode)
    }
  }

  def getSubCodesFromTopCode(topCode : Code) : List[Code] = {

    val midCodes = getCodes(crawl(midUrl(topCode.code)))
    topCode.subList = midCodes

    midCodes.par.foldLeft(List[Code]()){(xss , x)=>
      val leafCodes = getCodes(crawl(leafUrl(x.code)))
      x.subList = leafCodes
      xss ::: leafCodes
    }
  }

  def getCodes(response : String): List[Code] = {
    val json = Json.parse(response)

    json.validate[List[Code]] match {
      case JsSuccess(list: List[Code], _) => list
      case e: JsError => {
        println("Errors: " + JsError.toFlatJson(e).toString()) //throw
        List()
      }
    }
  }

  private def crawl(url : String) : String = {

    val ws = WS.url(url)
    val response = Await.result(ws.withFollowRedirects(false).get(), 3000 millis)

    new String(response.body.getBytes("ISO-8859-1"), "UTF-8")  // fix to utf8. getting and setting from header is more correct.
  }

  def showAllRegions : String = {
    crawl
    val result = showAllRegions(topCodes, 0)
    result.size.toString()
  }
  private def showAllRegions(list : List[Code], count : Int) : List[Code] = {

    list.foldLeft(List[Code]()) { (xss, x) =>
      if (x.subList.nonEmpty)
        x :: xss ::: showAllRegions(x.subList, count + 1)
      else
        x :: xss
    }
  }

  def getTownWeather(code : Code): String = {
    //예외처리
    def townWeatherUrl(code : String) = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=%s".format(code)
    if( code.code.length != 10)
      return ""

    val ws = WS.url(townWeatherUrl(code.code))
    val response = Await.result(ws.withFollowRedirects(false).get(), 3000 millis)

    response.body
  }

}

/**
implicit val residentReads: Reads[Resident] = (
  (JsPath \ "name").read[String] and
  (JsPath \ "age").read[Int] and
  (JsPath \ "role").readNullable[String]
)(Resident.apply _)
  val nameReads: Reads[String] = (JsPath \ "name").read[String]
val nameResult: JsResult[String] = json.validate[String](nameReads)
nameResult match {
  case s: JsSuccess[String] => println("Name: " + s.get)
  case e: J
  */