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

final case class Cat(name: String, age: Int, color: String)
object Cat {
  import cats.syntax.show._
  import cats.std.int._
  import cats.std.string._

  implicit val catShow = Show.show[Cat] { cat =>
    val name = cat.name.show
    val age = cat.age.show
    val color = cat.color.show

    s"$name is a $age year-old $color cat."
  }
}

object CatShowDriver extends App {
  import Cat._

  val tabby = Cat("Tabby", 4, "reddish-brown")

  import cats.syntax.show._
  println(tabby.show)
}