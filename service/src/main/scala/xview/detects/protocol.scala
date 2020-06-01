package xview.detects

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object protocol {
  case class Unstructured(text: String)
  case class Structured(text: String)

  case class Pt(x: Int, y: Int)
  object Pt extends DefaultJsonProtocol {
    implicit val fmt: RootJsonFormat[Pt] = jsonFormat2(Pt.apply)
  }
  case class Record(p0: Pt, p1: Pt, `class`: String, confidence: String)
  object Record extends DefaultJsonProtocol {
    implicit val fmt: RootJsonFormat[Record] = jsonFormat4(Record.apply)
  }
}

//"file": {
//    "record": [
//      {
//        "p0": [
//          "376",
//          "180"
//        ],
//        "p1": [
//          "393",
//          "207"
//        ],
//        "class": "4",
//        "confidence": "0.9652593"
//      },
