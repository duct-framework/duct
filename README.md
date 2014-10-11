# Duct

Duct is a minimal framework for building web applications in Clojure,
with a strong emphasis on [simplicity][].

Duct is **experimental** and **unreleased** software. Stay tuned.

[simplicity]: http://www.infoq.com/presentations/Simple-Made-Easy


## Usage

Create a new Duct project with Leiningen.

```sh
lein new duct <<your project name>>
```

Change directory into your new project.

```sh
cd <<your project name>>
```

Then check your new project's `README.md` file for instructions on
setting up a development environment.


## Concepts

Duct consists of a [Leiningen][] template and a small support library.

Duct depends on existing libraries for the majority of its functionality.

Externally, Duct follows the [Twelve-Factor App][12-factor] methodology.

Internally, Duct uses Stuart Sierra's [Reloaded Workflow][reloaded].

Duct prefers local bindings over global state.

Duct separates configuration and environment.

Duct applications are divided by purpose, rather than layer.

[leiningen]: https://github.com/technomancy/leiningen
[12-factor]: http://12factor.net/
[reloaded]:  http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded
[component]: https://github.com/stuartsierra/component


## License

Copyright © 2014 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
