# sbt-sass-compile

`sbt-sass-compile` is a very simple SBT plugin to compile SCSS files to CSS.

It intentionally does not contain any bells and whistles.

## Usage

In `project/plugins.sbt`

    lazy val root = project.in(file(".")).dependsOn(uri("git://github.com/braunse/sbt-sass-compile.git"))

a