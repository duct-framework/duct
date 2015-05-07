# Duct

Duct is a minimal framework for building web applications in Clojure,
with a strong emphasis on [simplicity][].

[simplicity]: http://www.infoq.com/presentations/Simple-Made-Easy


## Usage

Create a new Duct project with Leiningen.

```sh
lein new duct <<your project name>>
```

This will create a minimal Duct project. You can extend this by
appending profile hints to add extra functionality.

* `+example`  adds an example endpoint
* `+heroku`   adds configuration for deploying to Heroku
* `+postgres` adds a PostgreSQL dependency and database component
* `+site`     adds site middleware, a favicon, webjars and more

For example:

```sh
lein new duct foobar +site +example
```

As with all Leiningen templates, Duct will create a new directory with
the same name as your project. For information on how to run and build
your project, refer to the project's `README.md` file.


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


## Overview

Duct is designed to produce a standalone web application, configured
with environment variables, and logging to STDOUT. Typically it will
sit behind a proxy or load-balancer, and works well in environments
like [Heroku][] and [Docker][].

Internally, Duct projects are structured with the [Component][]
library. Components handle the lifecycle of the web server, and
connections to other services and databases. It's highly recommended
you avoid any global state, and even dynamic bindings are discouraged.

The routes of the application are divided into **endpoints**. These
are functions that take a component map, and return a [Ring][] handler
function. Duct therefore relies on closures and lexical scoping to
pass database connections and other configuration data to the routes.

Endpoints should resemble microservices, grouping routes by purpose.
An endpoint might handle user authentication, or handle comments on a
post. Strive to keep your endpoints small and focused.

[heroku]: https://www.heroku.com/
[docker]: https://www.docker.com/
[component]: https://github.com/stuartsierra/component
[ring]:   https://github.com/ring-clojure/ring


## Documentation

* [Getting Started](https://github.com/weavejester/duct/wiki/Getting-Started)
* [Configuration](https://github.com/weavejester/duct/wiki/Configuration)
* [Components](https://github.com/weavejester/duct/wiki/Components)


## File structure

Duct projects are structured as below. Files marked with a * are kept
out of version control.

```handlebars
.
├── README.md
├── dev
│   ├── local.clj *
│   └── user.clj
├── profiles.clj *
├── project.clj
├── resources
│   ├── errors
│   ├── public
│   └── {{project}}
│       └── endpoint
│           └── {{endpoint}}
├── src
│   └── {{project}}
│       ├── component
│       ├── config.clj
│       ├── endpoint
│       │   └── {{endpoint}}.clj
│       ├── main.clj
│       └── system.clj
└── test
    └── {{project}}
        └── endpoint
            └── {{endpoint}}_test.clj
```


## License

Copyright © 2015 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
