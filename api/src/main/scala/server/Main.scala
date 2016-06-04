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

package server

import java.net.InetAddress

import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import core.CoreImpl

import scala.concurrent.Future

object Main extends App with Routes {
  val name = "june"

  def runnable(s: String): CoreImpl => Future[ServerBinding] = {
    run
  }

  def run(c: CoreImpl): Future[ServerBinding] = {
    import c._

    val server = InetAddress.getLocalHost.getHostName
    val port = 8080

    log.info(s"Starting the server {} at port {} on server", server, port)
    val binding = Http().bindAndHandle(handler = routes, interface = "0.0.0.0", port = port)

    binding onFailure {
      case ex: Exception ⇒
        log.error(s"Failed to bind to $server:$port!", ex)
    }

    binding
  }

  runnable(name)(CoreImpl(name))
}
