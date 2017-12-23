package quill

import io.getquill._

case class Person(name: String, age: Int)

object PersonApp extends App {
  // play with Person
  val ctx = new SqlMirrorContext(MirrorSqlDialect, Literal)
  import ctx._
  val myFunction = quote {
    (i: Int) => infix"MY_FUNCTION($i)".as[Int]
  }
  val q1 = quote {
    query[Person].filter(p => p.name == "John").map(p => myFunction(p.age))
  }
  val ast1 = q1.ast 
  println(ast1)
  val m1 = ctx.run(q1)
  val sqlCommand1 = m1.string
  println(sqlCommand1)

  val q2 = quote { query[Person].filter(p => p.name == "John") }
  val ast2 = q2.ast
  println(ast2) 
  val m2 = ctx.run(q2)
  val sqlCommand2 = m2.string
  println(sqlCommand2)

}
