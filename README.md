# Duct

Duct is a highly modular framework for building server-side
applications in Clojure using data-driven architecture.

It is similar in scope to [Arachne][], and is based on [Integrant][].
Duct builds applications around an immutable configuration that acts
as a structural blueprint. The configuration can be manipulated and
queried to produce sophisticated behavior.

[integrant]: https://github.com/weavejester/integrant
[arachne]: http://arachne-framework.org/


## Quick Start

To create a new Duct project with Leiningen:

```sh
lein new duct <your project name>
```

This will create a minimal Duct project. You can extend this by
appending profile hints to add extra functionality.

* `+api`      adds API middleware and handlers
* `+ataraxy`  adds the Ataraxy router
* `+cljs`     adds in ClojureScript compilation and hot-loading
* `+example`  adds an example handler
* `+heroku`   adds configuration for deploying to Heroku
* `+postgres` adds a PostgreSQL dependency and database component
* `+site`     adds site middleware, a favicon, webjars and more
* `+sqlite`   adds a SQLite dependency and database component

For example:

```sh
lein new duct foobar +site +example
```

As with all Leiningen templates, Duct will create a new directory with
the same name as your project. For information on how to run and build
your project, refer to the project's `README.md` file.


## Concepts

The structure of the application is defined by an Integrant configuration map.

The configuration map is transformed using modules.

In development, Duct uses Stuart Sierra's [Reloaded Workflow][reloaded].

In production, Duct follows the [Twelve-Factor App][12-factor] methodology.

Local state is preferred over global state.

Namespaces should group functions by purpose, rather than by layer.

[12-factor]: http://12factor.net/
[reloaded]: http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded


## Overview

Duct is designed to produce a standalone application, logging to
STDOUT, with external configuration supplied through environment
variables. This approach is common for server-side applications, and
works well in environments like [Heroku][] and [Docker][].

The core of every Duct application is the configuration map, loaded
from one or more [edn][] resources, and interpreted by Integrant.
Each key/value pair in the configuration corresponds to a multimethod
that can **initiate** the configuration into a concrete implementation.

Before the configuration is initiated, however, it is first transformed.
Some keys in the configuration are **modules**; these are pure
functions used to update the configuration, adding in new keys and
references.

Modules can introduce broad functionality that affects many parts of
the application. Because they manipulate an immutable data structure,
they are also both transparent and customizable. Anything a module
adds can be queried, examined, and if necessary, overridden.

Any I/O to an external service should be accessed through a
**boundary** protocol. This not only provides a clear dividing line
between what's internal and what's external to the application, it
also allows external services to be mocked or stubbed when testing.

[heroku]:    https://www.heroku.com/
[docker]:    https://www.docker.com/
[edn]:       https://github.com/edn-format/edn
[ring]:      https://github.com/ring-clojure/ring


## Documentation

* [Getting Started](https://github.com/weavejester/duct/wiki/Getting-Started)
* [Configuration](https://github.com/weavejester/duct/wiki/Configuration)


## Community

* [Google Group](https://groups.google.com/forum/#!forum/duct-clojure)


## File structure

Duct projects are structured as below. Files marked with a * are kept
out of version control.

```handlebars
{{project}}
├── README.md
├── dev
│   ├── resources
│   │   ├── dev.edn
│   │   └── local.edn *
│   └── src
│       ├── dev.clj
│       ├── local.clj *
│       └── user.clj
├── profiles.clj *
├── project.clj
├── resources
│   └── {{project}}
│       ├── config.edn
│       └── public
├── src
│   └── {{project}}
│       ├── boundary
│       │   └── {{boundary}}.clj
│       ├── handler
│       │   └── {{handler}}.clj
│       └── main.clj
└── test
    └── {{project}}
        ├── boundary
        │   └── {{boundary}}_test.clj
        └── handler
            └── {{handler}}_test.clj
```


## License

Copyright © 2017 James Reeves

Distributed under the MIT license.
