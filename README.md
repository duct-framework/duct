# Duct

Duct is a minimal framework for building web applications in Clojure,
with a strong emphasis on [simplicity][].

Duct is **experimental** and **unreleased** software. Stay tuned.

[simplicity]: http://www.infoq.com/presentations/Simple-Made-Easy


## Concepts

Duct consists of a [Leiningen][] template and a small support library.

Duct depends on existing libraries for the majority of its functionality.

Externally, Duct follows the [Twelve-Factor App][] methodology.

Internally, Duct uses Stuart Sierra's [Reloaded Workflow][].

Duct discourages global state and dynamic bindings.

Duct encourages lexical scoping and component isolation.

[Leiningen]: https://github.com/technomancy/leiningen
[Twelve-Factor App]: http://12factor.net/
[Reloaded Workflow]: http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded


## Usage

Create a new Duct project with Leiningen.

```sh
lein new duct <<your project name>>
```

Change directory into your new project.

```sh
cd <<your project name>>
```

Then start a REPL.

```sh
lein repl
```


## License

Copyright © 2014 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
