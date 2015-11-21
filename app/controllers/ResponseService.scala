package controllers

import play.api.libs.json.{JsObject, Json}

object ResponseService {
  def success(msg : JsObject) : JsObject =
    Json.obj(
      "returnCode" -> 200,
      "msg" -> msg
    )

  def success(msg : String) : JsObject =
    Json.obj(
    "returnCode" -> 200,
    "msg" -> msg
    )

  def invalid(msg : String) : JsObject =
    Json.obj(
    "returnCode" -> 400,
    "msg" -> msg
    )
}
