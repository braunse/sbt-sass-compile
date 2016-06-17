# sbt-sass-compile

`sbt-sass-compile` is a very simple SBT plugin to compile SCSS files
to CSS.

It intentionally does not contain any bells and whistles.

## Usage

In `project/plugins.sbt`:

    lazy val buildProject = project.in(file("."))
      .dependsOn(uri("git://github.com/braunse/sbt-sass-compile.git"))

And in `build.sbt` (minimal configuration):

    lazy val rootProject = project.in(file("."))
      .enablePlugins(SassCompilerPlugin)

This will compile all SCSS files in `src/main/frontend` into a `css`
resource package.

Full configuration example:

    lazy val rootProject = project.in(file("."))
      .enablePlugins(SassCompilerPlugin)
      .settings(
        sassSourceRoot in (Compile, compileSass) := file("./src/main/scss"),
        sassCompiler := "node-sass",
        sassOutputDir in (Compile, compileSass) := (resourceManaged in Compile).value / "my/css/package"
      )
