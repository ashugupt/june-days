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

import sbt._

object Version {
  final val Scala         = "2.11.8"

  final val slf4j         = "1.7.21"
  final val logback       = "1.1.7"

  final val cats          = "0.6.0"

  final val circe         = "0.5.0-M1"

  final val akka          = "2.4.7"
  final val akkaHttpJson  = "1.6.0"

  final val postgresql    = "9.4-1206-jdbc42"

  final val slick         = "3.1.1"
  final val slickPg       = "0.14.1"

  final val junitVer      = "4.12"
  final val scalaTestVer  = "3.0.0-RC1"
  final val scalaCheckVer = "1.13.1"
}

object Dependencies {

  import Version._

  /////////////////// Logging
  val slf4jApi             = "org.slf4j"               %  "slf4j-api"                           % slf4j
  val logbackApi           = "ch.qos.logback"          %  "logback-classic"                     % logback

  /////////////////// Cats
  val catsMacros           = "org.typelevel"           %% "cats-macros"                         % cats
  val catsKernel           = "org.typelevel"           %% "cats-kernel"                         % cats
  val catsCore             = "org.typelevel"           %% "cats-core"                           % cats
  val catsLaws             = "org.typelevel"           %% "cats-laws"                           % cats
  val catsFree             = "org.typelevel"           %% "cats-free"                           % cats

  /////////////////// Akka
  val akkaActor            = "com.typesafe.akka"       %% "akka-actor"                          % akka
  val akkaStream           = "com.typesafe.akka"       %% "akka-stream"                         % akka
  val akkaSlf4j            = "com.typesafe.akka"       %% "akka-slf4j"                          % akka
  val akkaHttpCore         = "com.typesafe.akka"       %% "akka-http-core"                      % akka
  val akkaHttpExperimental = "com.typesafe.akka"       %% "akka-http-experimental"              % akka
  val akkaPersistence      = "com.typesafe.akka"       %% "akka-persistence"                    % akka
  val akkaPersistenceQuery = "com.typesafe.akka"       %% "akka-persistence-query-experimental" % akka
  val akkaPersistenceTck   = "com.typesafe.akka"       %% "akka-persistence-tck"                % akka
  val akkaTyped            = "com.typesafe.akka"       %% "akka-typed-experimental"             % akka
  val akkaCluster          = "com.typesafe.akka"       %% "akka-cluster"                        % akka
  val akkaClusterSharding  = "com.typesafe.akka"       %% "akka-cluster-sharding"               % akka
  val akkaClusterMetrics   = "com.typesafe.akka"       %% "akka-cluster-metrics"                % akka
  val akkaClusterTools     = "com.typesafe.akka"       %% "akka-cluster-tools"                  % akka
  val akkaDistributedData  = "com.typesafe.akka"       %% "akka-distributed-data-experimental"  % akka

  //////////////////// Circe
  val circeCore            = "io.circe"                %% "circe-core"                          % circe
  val circeJawn            = "io.circe"                %% "circe-jawn"                          % circe
  val circeGeneric         = "io.circe"                %% "circe-generic"                       % circe
  val circeParser          = "io.circe"                %% "circe-parser"                        % circe
  val circeOptics          = "io.circe"                %% "circe-java8"                         % circe
  val circeJava8           = "io.circe"                %% "circe-optics"                        % circe

  //////////////////// Akka-http-circe
  val akkaHttpCirce        = "de.heikoseeberger"       %% "akka-http-circe"                     % akkaHttpJson

  //////////////////// Postgresql
  val postgres             = "org.postgresql"          %  "postgresql"                          % postgresql

  //////////////////// Slick
  val slickCore            = "com.typesafe.slick"      %% "slick"                               % slick
  val slickHikariCP        = "com.typesafe.slick"      %% "slick-hikaricp"                      % slick
  val slickCodegen         = "com.typesafe.slick"      %% "slick-codegen"                       % slick

  /////////////////// Slick-PG
  val slickPgCore          = "com.github.tminglei"     %% "slick-pg"                            % slickPg
  val slickPgDate          = "com.github.tminglei"     %% "slick-pg_date2"                      % slickPg
  val slickPgCirce         = "com.github.tminglei"     %% "slick-pg_circe-json"                 % slickPg

  /////////////////// Testing
  val junit                = "junit"                %  "junit"                        % junitVer      % "test"
  val scalaCheck           = "org.scalacheck"       %% "scalacheck"                   % scalaCheckVer % "test"
  val scalaTest            = "org.scalatest"        %% "scalatest"                    % scalaTestVer  % "test"
  val akkaTestkit          = "com.typesafe.akka"    %% "akka-testkit"                 % akka          % "test"
  val akkaMultinodeTestkit = "com.typesafe.akka"    %% "akka-multi-node-testkit"      % akka          % "test"
  val akkaStreamTestkit    = "com.typesafe.akka"    %% "akka-stream-testkit"          % akka          % "test"
  val akkaHttpTestkit      = "com.typesafe.akka"    %% "akka-http-testkit"            % akka          % "test"
  val slickTestkit         = "com.typesafe.slick"   %% "slick-testkit"                % slick         % "test"
}