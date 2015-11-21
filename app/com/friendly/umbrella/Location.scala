package com.friendly.umbrella

import java.io.File

import play.api.{Play, Logger}

import scala.collection.mutable
import scala.io.Source

/**
 * Created by YeonjuMac on 15. 11. 21..
 */

case class LocationCode(regionName : String, code : String) {
  val codeLength = code.length
  var subLocationCode : mutable.LinkedHashMap[String, LocationCode] = new mutable.LinkedHashMap[String, LocationCode]()
}

object Location {

  val locationCodePath = Play.current.configuration.getString("locationCode.path").getOrElse{throw new IllegalArgumentException("locationCode.path is not set")}

  val locationCodes = Source.fromFile(new File(locationCodePath)).getLines().toList
  val locationMap = locationCodes.map{line => LocationCode(line.split("\t")(1), line.split("\t")(0)) }.groupBy(_.codeLength)
  val topList = locationMap(2).map{x=> x.code->x}.toMap
  val midList = locationMap(5).map{x=>topList(x.code.substring(0,2)).subLocationCode += x.regionName->x;x}.map{x=> x.code->x}.toMap
  locationMap(10).map{x=>midList(x.code.substring(0, 5)).subLocationCode += x.regionName->x;x}

  val topMap = mutable.LinkedHashMap(topList.map{x=> x._2.regionName -> x._2}.toSeq:_*)

  def find(regionFullName : String) : LocationCode = {
    val regionNames = regionFullName.split(" ")

    var currentMap =  topMap
    var currentRegion : LocationCode = topMap.head._2

    println("currentRegion"+currentRegion)

    regionNames.foreach { regionName=>
      currentMap.get(regionName) match {
        case Some(x)=>
          currentRegion = x
          currentMap = x.subLocationCode

          if(currentMap.isEmpty) return currentRegion
        case None =>
      }
    }

    currentRegion.codeLength match {
      case 2 =>
        Logger.info("Can't find correct location code. Use default RegionCode" + regionFullName)
        currentRegion.subLocationCode.head._2.subLocationCode.head._2
      case 5 =>
        Logger.info("Can't find correct location code." + regionFullName)
        currentRegion.subLocationCode.head._2
      case _ =>
        Logger.info("Please Check what was wrong." + regionFullName)
        topMap.head._2.subLocationCode.head._2.subLocationCode.head._2
    }
  }
}
