import java.io.File

import org.apache.daffodil.sapi.Daffodil
import org.apache.daffodil.sapi.infoset.JsonInfosetOutputter
import org.apache.daffodil.sapi.io.InputSourceDataInputStream

import scala.io.Source

object main extends App {
  val schema = getClass.getResource("xview/xsd/detects.dfdl.xsd").getPath

  val c = Daffodil.compiler()
  val pf = c.compileFile(new File(schema))
  pf.getDiagnostics.filter(_.isError).foreach(println)

  val dp = pf.onPath("/")

  val input = Source.fromResource("test1.txt").getLines().mkString("\n")
  dp.parse(new InputSourceDataInputStream(input.getBytes), new JsonInfosetOutputter(System.out, true))
}
