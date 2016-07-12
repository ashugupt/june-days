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
  import io.circe.generic.auto._
  import io.circe.java8.time._

  case class Packet(username: String,
                    host: String,
                    port: Int,
                    num_processors: Int,
                    free_memory: String,
                    total_memory: String,
                    free_disk: String,
                    total_disk: String,
                    system_time: ZonedDateTime)

  case class BuildInf(name: String,
                      version: String,
                      scalaVersion: String,
                      sbtVersion: String,
                      buildNumber: Int,
                      builtAt: String)

  val user = System.getProperty("user.name")
  val host = InetAddress.getLocalHost.getHostAddress
  val port = 8080
  val processors = Runtime.getRuntime.availableProcessors()
  val usableMemory = math.floor(Runtime.getRuntime.freeMemory() / (1024 * 1024))
  val totalMemory = math.floor(Runtime.getRuntime.totalMemory() / (1024 * 1024))
  val freeDisk = math.floor(Files.getFileStore(Paths.get("/")).getUnallocatedSpace / (1024 * 1024 * 1024))
  val totalDisk = math.floor(Files.getFileStore(Paths.get("/")).getTotalSpace / (1024 * 1024 * 1024))

  val healthPacket = Packet(
    user,
    host,
    port,
    processors,
    s"$usableMemory MB",
    s"$totalMemory MB",
    s"$freeDisk GB",
    s"$totalDisk GB",
    ZonedDateTime.now())

  val build = BuildInf(BuildInfo.name, BuildInfo.version, BuildInfo.scalaVersion, BuildInfo.sbtVersion, BuildInfo.buildInfoBuildNumber, BuildInfo.builtAtString)

  val healthCheckRoute =
    pathPrefix("_build") {
      path("health") {
        (get | post | parameter('method ! "POST") | put) {
          pathEndOrSingleSlash {
            complete {
              println("Hello")
              healthPacket
            }
          }
        }
      } ~ path("buildinfo") {
        (get | post | parameter('method ! "POST") | put) {
          complete {
            build
          }
        }
      }
    }

}
