package xview.detects

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes.OK
import akka.http.scaladsl.server.Directives.{complete, extractExecutionContext, extractLog, get, path, pathPrefix}
import akka.http.scaladsl.server.Route

object http {

  trait Adapter {
    implicit def system: ActorSystem

    def routes(controller: ActorRef): Route =
      pathPrefix("api") {
        extractLog { logger ⇒
          extractExecutionContext { implicit ec ⇒
            path("health") {
              get {
                complete(OK)
              }
            }
            // post detects
          }
        }
      }
  }

}
