package quill

import io.getquill._

case class R0Tuple(x: Int, p: Double = TwoMapApp.myRandom(), q: Double = TwoMapApp.myRandom()) 
case class R1Tuple(x: Int, p: Double, q: Double, c:Double = Double.NaN, s:Double = Double.NaN)

object TwoMapApp extends App {
  def myRandom() : Double = {
    var r = new scala.util.Random()
    2 * 3.1416 * (r.nextInt(2000)/2001.0)
  }

  // play with Person
  val ctx = new SqlMirrorContext(MirrorSqlDialect, Literal)
  import ctx._

  // play whit R0
  val myRange = Range(1,8) // gera uma range de 1 a 7 inclusive

  val createR1Tuple = quote {
    (i: Int, v1: Double, v2: Double, v3: Double, v4: Double) => infix"CREATE_R1_Tuple($i, $v1, $v2, $v3, $v4)".as[R1Tuple]
  }

  val cos = quote {
      (v: Double) => infix"cos($v)".as[Double]
  }
  val sin = quote {
      (v: Double) => infix"sin($v)".as[Double]
  }
  val mapped = quote {
    query[R0Tuple].map((t) => createR1Tuple(t.x, t.p, t.q, cos(t.p), 10.0)).map((t) => createR1Tuple(t.x, t.p, t.c, t.q, sin(t.q)))
  }
  val ast1 = mapped.ast 
  println(ast1)
  val m1 = ctx.run(mapped)
  val sqlCommand1 = m1.string
  println(sqlCommand1)
}
