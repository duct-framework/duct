# {{name}}

FIXME: description

## Developing

### Setup

When you first clone this repository, run:

```sh
lein setup
```

This will create files for local configuration, and prep your system
for the project.{{#heroku?}}

Next connect the repository to the [Heroku][] app:

```sh
heroku git:remote -a FIXME
```

[heroku]: https://www.heroku.com/{{/heroku?}}

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to initiate and start the system.

```clojure
dev=> (go)
:started
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.{{#cljs?}} Changes to CSS or ClojureScript
files will be hot-loaded into the browser.{{/cljs?}}

```clojure
dev=> (reset)
:reloading (...)
:resumed
```
{{#cljs?}}

If you want to access a ClojureScript REPL, make sure that the site is loaded
in a browser and run:

```clojure
dev=> (cljs-repl)
Waiting for browser connection... Connected.
To quit, type: :cljs/quit
nil
cljs.user=>
```
{{/cljs?}}

### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests through Leiningen.

```sh
lein test
```

{{#ragtime?}}
### Migrations

Migrations are handled by [ragtime][]. Migration files are stored in
the `resources/migrations` directory, and are applied in alphanumeric
order.

To update the database to the latest migration, open the REPL and run:

```clojure
dev=> (migrate)
Applying 20150815144312-create-users
Applying 20150815145033-create-posts
```

To rollback the last migration, run:

```clojure
dev=> (rollback)
Rolling back 20150815145033-create-posts
```

Note that the system needs to be setup with `(init)` or `(go)` before
migrations can be applied.

[ragtime]: https://github.com/weavejester/ragtime

{{/ragtime?}}
### Generators

This project has several generator functions to help you create files.

To create a new endpoint:

```clojure
dev=> (gen/endpoint "bar")
Creating file src/foo/endpoint/bar.clj
Creating file test/foo/endpoint/bar_test.clj
Creating directory resources/foo/endpoint/bar
nil
```

To create a new component:

```clojure
dev=> (gen/component "baz")
Creating file src/foo/component/baz.clj
Creating file test/foo/component/baz_test.clj
nil
```

To create a new boundary:

```clojure
dev=> (gen/boundary "quz" foo.component.baz.Baz)
Creating file src/foo/boundary/quz.clj
Creating file test/foo/boundary/quz_test.clj
nil
```
{{#ragtime?}}

To create a new SQL migration:

```clojure
dev=> (gen/sql-migration "create-users")
Creating file resources/foo/migrations/20160519143643-create-users.up.sql
Creating file resources/foo/migrations/20160519143643-create-users.down.sql
nil
```
{{/ragtime?}}

## Deploying

{{^lein-deploy?}}FIXME: steps to deploy{{/lein-deploy?}}{{#lein-deploy?}}To deploy the project, run:

```sh
lein deploy
```{{/lein-deploy?}}

## Legal

Copyright © {{year}} FIXME
