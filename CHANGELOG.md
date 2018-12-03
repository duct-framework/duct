## 0.11.0-beta4 (2018-01-03)

* Updated `duct/module.sql` to 0.5.0-beta1 (#82)

## 0.11.0-beta3 (2018-11-10)

* Updated `duct/core` to 0.7.0-beta2 (#80)

## 0.11.0-beta2 (2018-10-31)

* Fixed out-of-date `.dir-locals.el` template (#79)
* Updated Clojure to 1.10.0-beta4
* Updated `duct/module.logging` to 0.4.0-beta1
* Updated `duct/module.web` to 0.7.0-beta1
* Updated SQLite dependency

## 0.11.0-beta1 (2018-10-21)

* Fixed ambiguous key error with `:duct.profile/base` (#77)
* Updated Clojure to 1.10.0-beta3
* Updated `duct/core` to 0.7.0-beta1
* Updated `duct/module.cljs` to 0.7.0-beta1

## 0.11.0-alpha6 (2018-10-06)

* Improved support for non-web projects
* Added non-web example service
* Update Clojure version to 1.10.0-beta1

## 0.11.0-alpha5 (2018-10-01)

* Rearranged config.edn template
* Removed web app assumption when run without profile hints
* Updated +cljs and +ataraxy hints to work without +site or +api

## 0.11.0-alpha4 (2018-09-28)

* Fixed +site and +api profile hints conflicting
* Updated `duct/core` to 0.7.0-alpha8
* Updated `duct/module.web` to 0.7.0-alpha3
* Updated `duct/module.ataraxy` to 0.3.0-alpha3
* Updated Eftest to 0.5.3
* Updated PostgreSQL dependency

## 0.11.0-alpha3 (2018-09-21)

* Fixed bug in dev database configuration

## 0.11.0-alpha2 (2018-08-08)

* Fixed template namespace and function name

## 0.11.0-alpha1 (2018-05-30)

* Updated template to use new Duct configurations
* Updated `duct/core` to 0.7.0-alpha5
* Updated `duct/module.logging` to 0.4.0-alpha1
* Updated `duct/module.web` to 0.7.0-alpha1
* Updated `duct/module.ataraxy` to 0.7.0-alpha1
* Updated `duct/module.cljs` to 0.7.0-alpha1
* Updated `duct/module.sql` to 0.7.0-alpha1
* Updated Eftest to 0.5.2
* Updated Integrant-REPL to 0.3.1
* Updated PostgreSQL and SQLite dependencies

## 0.10.6 (2017-12-17)

* Updated Clojure version to 1.9.0
* Updated `duct/core` to 0.6.2
* Updated `duct/module.web` to 0.6.4
* Updated `duct/module.cljs` to 0.3.2
* Updated `org.xerial/sqlite-jdbc` to 3.21.0.1
* Updated Eftest to 0.4.1

## 0.10.5 (2017-11-11)

* Updated Clojure version to 1.9.0-RC1
* Updated `duct/module.sql` to 0.4.2

## 0.10.4 (2017-11-03)

* Updated Clojure version to 1.9.0-beta4
* Updated `duct/module.web` to 0.6.3
* Updated `duct/module.sql` to 0.4.1
* Updated third party dependencies
* Fixed example handler test failing on +ataraxy hint (#67)

## 0.10.3 (2017-10-18)

* Updated Clojure version to 1.9.0-beta2

## 0.10.2 (2017-09-05)

* Updated `duct/module.web` to 0.6.2

## 0.10.1 (2017-09-04)

* Updated Clojure version to 1.9.0-alpha19
* Updated `duct/module.web` to 0.6.1
* Updated `duct/module.logging` to 0.3.1
* Updated `duct/module.cljs` to 0.3.1
* Updated `duct/module.sql` to 0.3.2

## 0.10.0 (2017-08-24)

* Improved performance by only loading namespaces for relevant keys
* Improved performance of modules and core by removing non-essential requires
* Added support for `duct_hierarchy.edn` for derives without namespaces
* Added custom data reader support to `prep`
* Moved `prep` out of `exec` to allow for intermediate transformations
* Updated `duct/core` to 0.6.1
* Updated `duct/module.logging` to 0.3.0
* Updated `duct/module.web` to 0.6.0
* Updated `duct/module.ataraxy` to 0.2.0
* Updated `duct/module.cljs` to 0.3.0
* Updated `duct/module.sql` to 0.3.0

## 0.9.2 (2017-07-20)

* Updated `duct/core` to 0.5.1
* Updated `duct/module.web` to 0.5.5
* Added explicit `set-refresh-dirs` to `dev.clj` template

## 0.9.1 (2017-06-24)

* Updated `duct/core` to 0.5.0
* Updated `duct/module.web`  to 0.5.4
* Updated `duct/module.cljs` to 0.2.3
* Updated SQLite and Postgres dependencies
* Updated Eftest to 0.3.1
* Fixed typo in Ataraxy example handler (#57)

## 0.9.0 (2017-06-15)

* Changed Leiningen plugin license to MIT
* Updated `duct/module.web` to 0.5.1
* Updated `duct/module.ataraxy` to 0.1.6
* Removed `:duct` key from project template
* Fixed path in Heroku Procfile

## 0.9.0-beta1 (2017-06-02)

* Updated `duct/core` to 0.4.0
* Updated `duct/module.sql` to 0.2.2
* Updated `duct/module.cljs` to 0.2.2 (fixes Figwheel error)
* Removed `lein duct compile` subtask in favor of `lein run :duct/compiler`

## 0.9.0-alpha8 (2017-05-29)

* Updated `duct/module.ataraxy` to 0.1.4 (fixes error with empty routes)
* Updated `duct/module.sql` to 0.2.1 (fixes warning message with Schema)

## 0.9.0-alpha7 (2017-05-28)

* Added `+api` profile hint
* Updated `duct/module.web` to 0.5.0
* Updated `duct/module.ataraxy` to 0.1.3

## 0.9.0-alpha6 (2017-05-22)

* Renamed `endpoint` directory to `handler` (see 5804824)
* Replaced `clojure.pprint/pprint` with `fipp.edn/pprint`
* Added `+ataraxy` profile hint
* Added `.dir-locals.el` to `lein duct setup`
* Added logs directory to `.gitignore` (#55)
* Updated `duct/core` to 0.3.3
* Updated `duct/module.web` to 0.4.0
* Updated example handler to be more concise
* Fixed `dev.edn` template for project names with dashes (#54)

## 0.9.0-alpha5 (2017-05-10)

* Updated `duct/module.web` to 0.3.2 (fixes issues with `:duct.core/include`)
* Added `:duct.core/include` to `dev.edn` and `local.edn`
* Moved `local.edn` reference into `local.clj`

## 0.9.0-alpha4 (2017-05-07)

* Updated `duct/module.web` to 0.3.0 (adds routers)
* Updated `duct/core` to 0.2.3 (adds `:duct.core/handler` and `#duct/import`)
* Updated Clojure version in template to 1.9.0-alpha16

## 0.9.0-alpha3 (2017-04-25)

* Updated Integrant to 0.4.0 (this changes the `#ref` tag to `#ig/ref`)
* Updated modules to be self-ordering
* Removed now unnecessary `duct.core/modules` key

## 0.9.0-alpha2 (2017-04-18)

* Removed profiles and middleware from lein-duct plugin
* Updated Ring dependencies to 1.6.0-RC3

## 0.9.0-alpha1 (2017-04-18)

* Changed Leiningen template license to MIT
* Changed Duct to use Integrant rather than Component
* Removed `duct/duct` project
* Added a `duct/lein-duct` plugin project
* Renamed the Leiningen template `duct-alpha` until release
* Moved most of the template functionality to separate module libraries

## 0.8.2 (2016-10-03)

* Updated dependencies

## 0.8.1 (2016-10-01)

* Added var tag to configuration and deprecated use of raw symbols
* Fixed bug with shutdown hook not stopping running system

## 0.8.0 (2016-07-01)

* Replaced manual system construction with `duct.util.system/load-system`
* Added `dev/src` and `dev/resources` directories to template
* Moved `dev/user.clj` and `dev/dev.clj` to `dev/src`
* Moved `dev/dev/locals.clj` to `dev/src/locals.clj`
* Removed `dev/dev/tasks.clj` in favor of `duct.util.repl` namespace

## 0.7.0 (2016-06-03)

* Added `duct.generate/boundary` for generating boundary protocols
* Moved `locals.clj` to `dev/locals.clj` in locals generator
* Added [cljs-devtools][] to `+cljs` profile

[cljs-devtools]: https://github.com/binaryage/cljs-devtools

## 0.6.1 (2016-05-19)

* Added `duct.generate/sql-migration` for generating Ragtime SQL migrations
* Added `.dir-locals.el` to files generated by setup for better CIDER integration

## 0.6.0 (2016-05-18)

* Moved setup from `user` namespace into `dev` namespace
* Added `user/dev` function, similar to Stuart Sierra's reloaded template
* Added a `dev.tasks` namespace to contain development task functions
* Replaced Leiningen generators with functions in `duct.generate` namespace
* Updated dependencies
