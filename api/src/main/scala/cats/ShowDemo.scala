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
package cats

trait Printable[A] {
  def format(value: A): String

  def print(value: A): Unit
}

object DefaultPrintables {
  import cats.std.int._
  import cats.std.string._

  implicit val intPrintable = new Printable[Int] {
    override def format(value: Int): String = Show[Int].show(value)

    override def print(value: Int): Unit = println(format(value))
  }

  implicit val stringPrintable = new Printable[String] {
    override def format(value: String): String = Show[String].show(value)

    override def print(value: String): Unit = println(format(value))
  }
}

object ToPrintable {
  def format[A](value: A)(implicit printable: Printable[A]): String = printable.format(value)

  def print[A](value: A)(implicit printable: Printable[A]): Unit = println(format(value))
}

case class PrintableCat(name: String, age: Int, color: String)

object PrintableCat {
  import DefaultPrintables._

  implicit val catPrintable = new Printable[PrintableCat] {

    override def format(value: PrintableCat): String = {
      val name = ToPrintable.format(value.name)
      val age = ToPrintable.format(value.age)
      val color = ToPrintable.format(value.color)

      s"$name is a $age year-old $color cat."
    }

    override def print(value: PrintableCat): Unit = println(format(value))
  }
}

object ShowDemoDriver {
  def main(args: Array[String]): Unit = {
    val tabby = PrintableCat("Tabby", 7, "gray")
    val meatloaf = PrintableCat("Meatloaf", 4, "rust")

    ToPrintable.print(tabby)
    ToPrintable.print(meatloaf)
  }
}
