/*
 * Copyright [2016] Ashu Gupta <ashu.a.gupta@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package typeclasses.cats

trait Printable[A] {
  def format(value: A): String
}

object PrintDefaults {
  implicit val intPrintable = new Printable[Int] {
    override def format(value: Int): String = value.toString
  }

  implicit val stringPrintable = new Printable[String] {
    override def format(value: String): String = value
  }
}

object Print {
  def format[A](value: A)(implicit printer: Printable[A]): String = printer.format(value)

  def print[A](value: A)(implicit printer: Printable[A]): Unit = println(printer.format(value))
}

object PrintSyntax {
  implicit class PrintOps[A](value: A) {
    def format(implicit printer: Printable[A]): String = printer.format(value)

    def print(implicit printer: Printable[A]): Unit = println(printer.format(value))
  }
}

final case class Cat(name: String, age: Int, color: String)
object Cat {
  import PrintDefaults._

  implicit val catPrinter = new Printable[Cat] {
    override def format(value: Cat): String =
      s"${Print.format(value.name)} is a ${Print.format(value.age)} year-old ${Print.format(value.color)} cat."
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    import PrintSyntax._

    val cat = Cat("Tabby", 7, "Grey-Red")
    cat.print
  }
}
