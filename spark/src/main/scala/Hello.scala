import io.getquill._

import scala.concurrent.Future

object Hello extends App {
  val ctx = new CassandraAsyncContext[SnakeCase]("cassandra")

  import ctx._

  import scala.concurrent.ExecutionContext.Implicits.global

  case class Emp(emp_id: Int, emp_city: String, emp_name: String, emp_phone: Double, emp_sal: Double)

  object Emp {
    val getById = quote { id: Int =>
      query[Emp].filter(_.emp_id == id)
    }
  }

//  val result = ctx.run(Emp.getById)(1)
  val result = ctx.run(Emp.getById)(1)

  println(result)
}