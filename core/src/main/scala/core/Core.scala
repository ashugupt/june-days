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

package core

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

trait Core {
  def system: ActorSystem

  def materializer: ActorMaterializer

  def ec: ExecutionContext

  def timeout: Timeout

  def log: LoggingAdapter
}

final case class CoreImpl(name: String) extends Core {
  override implicit val system: ActorSystem = ActorSystem(name)

  override implicit val materializer: ActorMaterializer = ActorMaterializer()

  override implicit val ec: ExecutionContext = system.dispatcher

  override implicit val timeout: Timeout = Timeout(5 seconds)

  override val log: LoggingAdapter = Logging(system, getClass)

}
