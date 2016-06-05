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

package health

import java.net.InetAddress
import java.nio.file.{Files, Paths}
import java.time.ZonedDateTime

import akka.http.scaladsl.server.Directives
import de.heikoseeberger.akkahttpcirce.CirceSupport

trait HealthCheck {

  import CirceSupport._
  import Directives._
  import io.circe.java8.time._
  import io.circe.generic.auto._

  case class Packet(username: String,
                    host: String,
                    port: Int,
                    num_processors: Int,
                    free_memory: String,
                    total_memory: String,
                    free_disk: String,
                    total_disk: String,
                    version: String,
                    built_at: String,
                    system_time: ZonedDateTime)

  val user = System.getProperty("user.name")
  val host = InetAddress.getLocalHost.getHostAddress
  val port = 8080
  val processors = Runtime.getRuntime.availableProcessors()
  val usableMemory = math.floor(Runtime.getRuntime.freeMemory() / (1024 * 1024))
  val totalMemory = math.floor(Runtime.getRuntime.totalMemory() / (1024 * 1024))
  val freeDisk = math.floor(Files.getFileStore(Paths.get("/")).getUnallocatedSpace / (1024 * 1024 * 1024))
  val totalDisk = math.floor(Files.getFileStore(Paths.get("/")).getTotalSpace / (1024 * 1024 * 1024))

  val packet = Packet(
    user,
    host,
    port,
    processors,
    s"$usableMemory MB",
    s"$totalMemory MB",
    s"$freeDisk GB",
    s"$totalDisk GB",
    // This is generated by buildinfo plugin
    // IntelliJ code completion goes for a toss here.
    // https://github.com/sbt/sbt-buildinfo
    BuildInfo.version,
    BuildInfo.builtAtString,
    ZonedDateTime.now())

  val healthCheckRoute = path("health") {
    (get | post | parameter('method ! "POST") | put) {
      pathEndOrSingleSlash {
        complete {
          packet
        }
      }
    }
  }

}
