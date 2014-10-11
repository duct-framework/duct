# Duct

Duct is a minimal framework for building web applications in Clojure,
with a strong emphasis on [simplicity][].

Duct is **experimental** and **unreleased** software. Stay tuned.

[simplicity]: http://www.infoq.com/presentations/Simple-Made-Easy


## Concepts

Duct is built upon existing libraries, including [Ring][],
[Compojure][] and [Component][].

Externally, Duct follows the [Twelve-Factor App][] methodology.

Internally, Duct uses the [Reloaded Workflow][].

Duct discourages global state and dynamic binding.

Duct encourages lexical scoping and component isolation.

[Ring]: https://github.com/ring-clojure/ring
[Compojure]: https://github.com/weavejester/compojure
[Component]: https://github.com/stuartsierra/component
[Twelve-Factor App]: http://12factor.net/
[Reloaded Workflow]: http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded


## Getting Started

Create a new Duct project by running:

```sh
lein new duct <<your project name>>
```


## License

Copyright © 2014 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
