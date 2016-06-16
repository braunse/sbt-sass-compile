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

import sbt._

/**
  * Created by braunse on 16.06.16.
  */
object SassCompilerPlugin extends AutoPlugin {

  object autoImport {
    lazy val sassCompiler = settingKey[String]("The SASS compiler implementation to use. Supported compilers are sass, sassc and node-sass.")
    lazy val sassSourceRoot = settingKey[File]("The root folder where SASS sources will be searched")
    lazy val sassOutputDir = settingKey[File]("The output directory where CSS files will be put")
    lazy val compileSass = taskKey[Seq[File]]("Compile SASS to CSS")

    lazy val sassCompileBaseSettings: Seq[Def.Setting[_]] = Seq(
      compileSass := {
        CompileSass((sassCompiler in compileSass).value, (sassSourceRoot in compileSass).value,
          (sassOutputDir in compileSass).value, Keys.streams.value.log)
      },
      sassOutputDir in compileSass := {
        Keys.resourceManaged.in(compileSass).value / "css"
      },
      sassSourceRoot in compileSass := {
        Keys.sourceDirectory.in(compileSass).value / "frontend"
      },
      Keys.resourceGenerators <+= compileSass
    )
  }

  import autoImport._

  override def projectSettings: Seq[_root_.sbt.Def.Setting[_]] =
    inConfig(Compile)(sassCompileBaseSettings)

  override def globalSettings: Seq[_root_.sbt.Def.Setting[_]] = Seq(
    sassCompiler.in(GlobalScope) := "sass"
  )
}
