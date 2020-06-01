package xview.detects

import akka.actor.ActorRef
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl.Sink
import akka.util.{ByteString, Timeout}
import xview.detects.protocol.{Structured, Unstructured}

import scala.concurrent.duration.DurationInt

object http {
  implicit val to: Timeout = Timeout(1.second)

  trait Adapter {
    def routes(handler: ActorRef): Route =
      pathPrefix("api") {
        extractLog { logger ⇒
          extractMaterializer { implicit materializer =>
            path("health") {
              get {
                complete(OK)
              }
            } ~
              path("convert") {
                post {
                  extractRequest { req =>
                    val f = req.entity.dataBytes
                      .fold(ByteString.empty) {
                        case (acc, b) => acc ++ b
                      }
                      .map(_.utf8String)
                      .map(Unstructured)
                      .ask[Structured](handler)
                      .map(_.text)
                      .runWith(Sink.head)
                    complete(f)
                  }
                }
              }
          }
        }
      }
  }
}
