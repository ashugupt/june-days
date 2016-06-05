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

import akka.event.Logging.LogLevel
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.{HttpEntity, HttpRequest}
import akka.http.scaladsl.model.headers.{`Remote-Address`, `User-Agent`, `X-Forwarded-For`, `X-Real-Ip`}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.directives.{DebuggingDirectives, LogEntry, LoggingMagnet}
import akka.stream.Materializer
import akka.stream.scaladsl.Sink

import scala.concurrent.{ExecutionContext, Future}

trait RequestLoggingUtil {
  def logRequestResult(level: LogLevel, route: Route)
                      (implicit m: Materializer, ex: ExecutionContext) = {

    def dubugLoggingFunction(logger: LoggingAdapter)(req: HttpRequest)(res: Any): Unit = {
      val entry = res match {
        case Complete(resp) =>
          val ip = req.headers.collect {
            case `X-Forwarded-For`(x) => x
            case `X-Real-Ip`(x) => x.getAddress().get().getHostAddress
            case `Remote-Address`(x) => x.getAddress().get().getHostAddress
          }

          val userAgent = req.headers.collect {
            case `User-Agent`(x) => x
          }

          entityAsString(resp.entity).map(data => LogEntry(s"[Request - (Method: ${req.method.value}) (Uri: ${req.uri}) (User-Agent: ${userAgent.headOption.getOrElse(None)} ~ IP: ${ip.headOption.getOrElse("unknown")})] - [Response - (Status: ${resp.status}) (Entity: $data)]", level))
        case other =>
          Future.successful(LogEntry(s"$other", level))
      }
      entry.map(_.logTo(logger))
    }

    DebuggingDirectives.logRequestResult(LoggingMagnet(log => dubugLoggingFunction(log)))(route)
  }

  private def entityAsString(entity: HttpEntity)
                            (implicit m: Materializer, ex: ExecutionContext): Future[String] = {
    entity.dataBytes
      .map(_.decodeString(entity.contentType.charsetOption.map(_.value).getOrElse("UTF-8")))
      .runWith(Sink.head)
  }

}
