import play.PlayJava
import play.twirl.sbt.Import.TwirlKeys
import sbt.Keys._
import sbt._
import play.Play.autoImport._

name := "reactive-web"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

javacOptions ++= Seq("-target", "1.8", "-source", "1.8")

scalacOptions += "-feature"

TwirlKeys.templateFormats ++= Map("stream" -> "ui.StreamFormat.instance")

TwirlKeys.templateImports ++= Vector("_root_.ui.HtmlStream", "_root_.ui.HtmlStream._")

libraryDependencies ++= Seq(javaWs)
