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

package de.sebbraun.sbt.sasscompile

import java.io.File

import sbt._

/**
  * Created by braunse on 16.06.16.
  */
object CompileSass {
  def apply(sassCompiler: String, sassSourceDir: File, sassOutputDir: File, log: Logger): Seq[File] = {
    if (!sassSourceDir.exists()) {
      log.info(s"No sources found at $sassSourceDir")
      return Seq()
    }

    sassOutputDir.mkdirs()

    def compileSass(scss: File, optimize: Boolean): List[File] = {
      val outFile: File = sassOutputDir / scss.getName.replaceAll("(?i)\\.scss", if (optimize) ".opt.css" else ".css")
      val style = if (optimize) "compressed" else "nested"
      val commandLine = sassCompiler match {
        case "node-sass" =>
          val optargs = if (optimize) Nil else List("--source-map", "--source-map-contents")
          List("node-sass", "--output-style", style) ::: optargs ::: List(scss.getAbsolutePath, outFile.getAbsolutePath)
        case "sass" =>
          val optargs = if (optimize) Nil else List("--sourcemap=inline", "-l")
          List("sass", "--scss", "-t", style) ::: optargs ::: List(scss.getAbsolutePath, outFile.getAbsolutePath)
        case "sassc" =>
          val optargs = if (optimize) Nil else List("-m", "-l")
          List("sassc", "-t", style) ::: optargs ::: List(scss.getAbsolutePath, outFile.getAbsolutePath)
        case _ =>
          throw new IllegalArgumentException("")
      }

      log.debug(s"Compiling SCSS file $scss into $outFile")
      if (commandLine.! != 0) {
        throw new SassCompilerError("Compilation failed")
      }

      val mapFile = if (optimize)
        None
      else
        Some(sassOutputDir / scss.getName.replaceAll("(?i)\\.scss", ".css.map"))

      List(outFile) ++ mapFile.toList
    }

    log.info(s"Compiling SCSS files from $sassSourceDir to $sassOutputDir using $sassCompiler")
    for {
      scss <- (sassSourceDir * "*.scss").get if !scss.getName.startsWith("_")
      optimize <- Seq(true, false)
      output <- compileSass(scss, optimize)
    } yield {
      output
    }
  }
}
