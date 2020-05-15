package xview

import java.io.File

import org.apache.daffodil.sapi.Daffodil

object main extends App {
  val schema = getClass.getResource("xsd/detects.dfdl.xsd").getPath

  val c = Daffodil.compiler()
  val pf = c.compileFile(new File(schema))
  pf.getDiagnostics.foreach(println)
}
