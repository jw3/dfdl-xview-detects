package xview.detects

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer
import com.typesafe.scalalogging.LazyLogging
import net.ceedubs.ficus.Ficus._

object boot extends App with http.Adapter with LazyLogging {
  implicit val system = ActorSystem()
  implicit val materializer = Materializer.createMaterializer(system)

  logger.info("booting...")
  val config = system.settings.config
  val host = config.as[String]("http.host")
  val port = config.as[Int]("http.port")

  ///
  val handler = system.actorOf(Handler.props())

  logger.info("http interface starting on {}:{}", host, port.toString)
  Http().bindAndHandle(routes(handler), host, port)
}
