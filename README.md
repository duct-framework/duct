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


## Details

Duct is designed to produce a standalone web application, configured
with environment variables, and logging to STDOUT. Typically it will
sit behind a proxy or load-balancer, and works well in environments
like [Heroku][] and [Docker][].

Internally, Duct projects are structured with the [Component][]
library. Components handle the lifecycle of the web server, and
connections to other services and databases. It's highly recommended
you avoid any global state, and even dynamic bindings are discouraged.

The routes of the application are divided into *endpoints*. These are
functions that take a component map, and return a [Ring][] handler
function. Duct therefore relies on closures and lexical scoping to
pass database connections and other configuration data to the routes.

Endpoints should resemble microservices, grouping routes by purpose.
An endpoint might handle user authentication, or handle comments on a
post. Strive to keep your endpoints small and focused.

[heroku]: https://www.heroku.com/
[docker]: https://www.docker.com/
[component]: https://github.com/stuartsierra/component
[ring]:   https://github.com/ring-clojure/ring


## License

Copyright © 2014 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
