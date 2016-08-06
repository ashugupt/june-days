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

package shapelessdemo

import shapeless._
import shapeless.poly._

object MakeBigger extends Poly1 {
  implicit def intCase = at[Int](_ * 100)

  implicit def stringCase = at[String](_.toUpperCase)
}

object GenericMakeBigger extends Poly1 {
  implicit def hnilCase = at[HNil](identity)

  implicit def hconCase[H, T <: HList](implicit tailCase: GenericMakeBigger.Case[T] {type Result <: HList}) =
    at[H :: T](list => list.head :: list.head :: tailCase(list.tail))
}

object GenericMakeBiggerWithAux extends Poly1 {
  implicit def hnilCase = at[HNil](identity)

  implicit def hconsCase[H, T <: HList, BT <: HList](implicit tailCase: Case.Aux[T, BT]) =
    at[H :: T](l => l.head :: l.head :: tailCase(l.tail))
}

object Main {
  def main(args: Array[String]): Unit = {
    println(MakeBigger(42))
    println(MakeBigger("foo"))
    println(GenericMakeBigger(true :: "foo" :: 12.4 :: HNil))
    println(GenericMakeBiggerWithAux(true :: "foo" :: 12.4 :: HNil))
  }
}
