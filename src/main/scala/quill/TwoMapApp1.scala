package quill

import io.getquill._

case class R0TTuple(x: Int, p: Double = TwoMapApp1.myRandom(), q: Double = TwoMapApp1.myRandom())  extends Embedded
case class R1TTuple(r0item: R0TTuple, v:Double, c_or_s: Int)

object TwoMapApp1 extends App {
  def myRandom() : Double = {
    var r = new scala.util.Random()
    2 * 3.1416 * (r.nextInt(2000)/2001.0)
  }

  // play with Person
  val ctx = new SqlMirrorContext(MirrorSqlDialect, Literal)
  import ctx._

  // play whit R0
  val myRange = Range(1,8) // gera uma range de 1 a 7 inclusive

  val updateR1TTuple = quote {
    (r: R1TTuple, t: Int) => infix"UPDATE_R1_TTuple($r, $t)".as[R1TTuple]
  }
  val cos = quote {
      (v: Double) => infix"cos($v)".as[Double]
  }
  val sin = quote {
      (v: Double) => infix"sin($v)".as[Double]
  }

  val r0q = quote {
    query[R0TTuple]
  }

  val mapped = quote {
    query[R1TTuple].map((t) => updateR1TTuple(t, 1)).map((t) => updateR1TTuple(t, 2))
  }
  val ast1 = mapped.ast 
  println(ast1)
  val m1 = ctx.run(mapped)
  val sqlCommand1 = m1.string
  println(sqlCommand1)
}
