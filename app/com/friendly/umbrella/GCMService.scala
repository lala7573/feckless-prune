package com.friendly.umbrella

import com.google.android.gcm.server.{Message, Result, Sender}

object GCMService {

  val GCM_SERVER : String = "gcm.googleapis.com"
  val GCM_PORT : Int = 5235

  val TEST_SERVER : String = "gcm-preprod.googleapis.com"
  val TEST_PORT : Int = 5236

  val GCM_ELEMENT_NAME : String = "gcm"
  val GCM_NAMESPACE : String = "google:mobile:data"

  val PROJECT_ID : String = "472878111084"
  val API_KEY = "AIzaSyAVmTBVe1ROIhVDUSqslcaSXKZ5IO70QoE"
  val numOfRetries = 3

  def send(regId : String) : (String, Result) = {
    val sender : Sender = new Sender(API_KEY)
    val msg : Message = new Message.Builder().addData("message", "this is test").addData("testNo", "1").build()

    val result : Result = sender.send(msg, regId, numOfRetries)
    println(regId)
    (regId, result)
  }
}