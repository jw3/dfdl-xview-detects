package xview.detects

import java.io.File

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.util.ByteString
import org.apache.daffodil.sapi.Daffodil
import org.apache.daffodil.sapi.infoset.JsonInfosetOutputter
import org.apache.daffodil.sapi.io.InputSourceDataInputStream
import xview.detects.protocol.{Structured, Unstructured}

object Handler {
  def props() = Props(new Handler)
  def make()(implicit system: ActorSystem) = system.actorOf(props())
}

class Handler extends Actor with ActorLogging {
  val schema = getClass.getResource("/xview/xsd/detects.dfdl.xsd").getPath
  val c = Daffodil.compiler()
  val pf = c.compileFile(new File(schema))
  pf.getDiagnostics.filter(_.isError).foreach(println)
  val dp = pf.onPath("/")

  def receive: Receive = {
    case Unstructured(txt) ⇒
      val osb = ByteString.createBuilder
      dp.parse(new InputSourceDataInputStream(txt.getBytes), new JsonInfosetOutputter(osb.asOutputStream, true))
      sender ! Structured(osb.result().utf8String)
  }
}
