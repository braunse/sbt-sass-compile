/*
 * Copyright (c) 2016 Sébastien Braun.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN
 * AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

organization := "de.sebbraun"

name := "sbt-sass-compile"

description := "An SBT plugin to compile SCSS files to CSS with no bells and whistles."

version := "0.0.1"

scalaVersion := "2.10.6"

licenses := Seq(
  "MIT" -> url("http://opensource.org/licenses/MIT")
)

developers := List(
  Developer("braunse",
    "Sebastien Braun",
    "sebastien@sebbraun.de",
    url("https://github.com/braunse")
  )
)

scmInfo := Some(ScmInfo(url("https://github.com/braunse/sbt-sass-compiler"),
  connection = "scm:git:https://github.com/braunse/sbt-sass-compiler",
  devConnection = Some("scm:git")))

homepage := Some(url("https://github.com/braunse/sbt-sass-compile"))

pomIncludeRepository := { _ => false }

publishTo := None

publishMavenStyle := true

sbtPlugin := true

    