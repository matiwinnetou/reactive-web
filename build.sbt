import play.PlayJava
import sbt.Keys._
import sbt._
import play.Play.autoImport._

name := "reactive-web"
version := "1.0-SNAPSHOT"

scalaVersion := "2.11.4"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

javacOptions ++= Seq("-target", "1.8", "-source", "1.8")
scalacOptions += "-feature"

play.twirl.sbt.Import.TwirlKeys.templateFormats ++= Map("stream" -> "ui.StreamFormat.instance")
play.twirl.sbt.Import.TwirlKeys.templateImports ++= Vector("_root_.ui.HtmlStream", "_root_.ui.HtmlStream._")

libraryDependencies ++= Seq(javaWs)
