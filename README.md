# Duct

Duct is a highly modular framework for building server-side
applications in Clojure using data-driven architecture.

It is similar in scope to [Arachne][], and is based on [Integrant][].
Duct builds applications around an immutable configuration that acts
as a structural blueprint. The configuration can be manipulated and
queried to produce sophisticated behavior.

[integrant]: https://github.com/weavejester/integrant
[arachne]: http://arachne-framework.org/


## Upgrading

See: [UPGRADING.md](https://github.com/duct-framework/duct/blob/master/UPGRADING.md).


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

In development, Duct uses Stuart Sierra's [Reloaded Workflow][reloaded].

In production, Duct follows the [Twelve-Factor App][12-factor] methodology.

Local state is preferred over global state.

Namespaces should group functions by purpose, rather than by layer.

Protocols should be used to wrap external APIs.

[12-factor]: http://12factor.net/
[reloaded]: http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded


## Structure

Duct adds a layer of abstraction on top of Integrant. In Integrant,
a configuration map is **initiated** into a running system map.

    ┌────────┐   ┌────────┐
    │ config ├──>│ system │
    └────────┘   └────────┘

In Duct, the configuration is initiated twice. The configuration is
first initiated into an intermediate configuration, which in turn is
initiated into the system:

    ┌────────┐   ┌──────────────┐   ┌────────┐
    │ config ├──>│ intermediate ├──>│ system │
    └────────┘   └──────────────┘   └────────┘

In the same way that higher-order functions allow us to abstract
common patterns of code, Duct's layered configurations allow us to
abstract common patterns of configuration.

Keys in a Duct configuration are expected to initiate into functions
that transform a configuration map. There are two broad types:
**profiles**, which merge their value into the configuration, and
**modules**, which provide more complex manipulation.


## Documentation

* [Getting Started](https://github.com/weavejester/duct/wiki/Getting-Started)
* [Configuration](https://github.com/weavejester/duct/wiki/Configuration)


## Community

* [Google Group](https://groups.google.com/forum/#!forum/duct-clojure)
* #duct on [Clojurians Slack](http://clojurians.net/)


## File structure

Duct projects are structured as below. Files marked with a * are kept
out of version control.

```handlebars
{{project}}
├── README.md
├── dev
│   ├── resources
│   │   ├── dev.edn
│   │   └── local.edn *
│   └── src
│       ├── dev.clj
│       ├── local.clj *
│       └── user.clj
├── profiles.clj
├── project.clj
├── resources
│   └── {{project}}
│       └── config.edn
├── src
│   ├── duct_hierarchy.edn
│   └── {{project}}
│       └── main.clj
└── test
    └── {{project}}
```


## License

Copyright © 2019 James Reeves

Distributed under the MIT license.
