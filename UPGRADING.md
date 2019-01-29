# Upgrading

## 0.11 from 0.10

Update the Duct dependencies in your project file:

```clojure

[duct/core "0.7.0"]
[duct/module.logging "0.4.0"]
[duct/module.web "0.7.0"]
[duct/module.ataraxy "0.3.0"]
[duct/module.cljs "0.4.0"]
[duct/module.sql "0.5.0"]
```

Update the `duct/lein-duct` plugin version:

```clojure
[duct/lein-duct "0.11.2"]
```

Change the `-main` function in `main.clj` and replace the `duct/prep`
and `duct/exec` steps with `duct/exec-config`. The `exec-config` key
will need an additional `profiles` argument that should be set to
`[:duct.profile/prod]`.

Your `-main` function should look something like:

```clojure
(defn -main [& args]
  (let [keys     (or (duct/parse-keys args) [:duct/daemon])
        profiles [:duct.profile/prod]]
    (-> (duct/resource "bar/config.edn")
        (duct/read-config)
        (duct/exec-config profiles keys))))
```

Between version 0.10 and 0.11 the way configuration files connect
together has changed.

First, add these new profile keys to your `config.edn` file:

```edn
:duct.profile/base  {}
:duct.profile/dev   #duct/include "dev"
:duct.profile/local #duct/include "local"
:duct.profile/prod  {}
```

Then remove the following keys from all your configuration files,
including `dev.edn` and `local.edn`:

* `:duct.core/include`
* `:duct.core/environment`

Finally, move all your non-module keys in `config.edn` into the
`:duct.profile/base` key. Your configuration should look something
like:

```edn
{:duct.profile/base
 {:duct.core/project-ns example
  ;; ... more non-module keys
  }

 :duct.profile/dev   #duct/include "dev"
 :duct.profile/local #duct/include "local"
 :duct.profile/prod  {}

 :duct.module/logging {}
 ;; ... more module keys
 }
```

If you have split your configuration up into multiple files, and have
updated your `:duct.core/include` key, then you'll need to change this
into `#duct/include` directives and profile keys that inherit from
`:duct/profile` or `:duct.profile/base` if you want the profile to be
included by default.

So for example, if you had a configuration with:

```edn
{:duct.core/include ["migrations"]}
```

Then you'd change it to:

```edn
{:duct.profile/migrations #duct/include "migrations"}
```

And in your `src/duct_hierarchy.edn` file, you'd add a derivation
like:

```edn
{:duct.profile/migrations [:duct.profile/base]}
```

If you have any custom modules in your application, these will have to
be changed as well. In 0.11, dependencies for modules are added with
`ig/prep-key`, and the transformation function returned by
`ig/init-key`.

So if you have something like this in 0.10:

```clojure
(defmethod ig/init-key :foo.module/example [_ opts]
  {:req [:some.existing/key]
   :fn  (fn [config] ...)})
```

In 0.11 this would be changed to:

```clojure
(defmethod ig/prep-key :foo.module/example [_ opts]
  (assoc opts :duct.core/requires (ig/ref :some.existing/module)))

(defmethod ig/init-key :foo.module/example [_ opts]
  (fn [config] ...))
```

Note that dependencies added by prep can only be between modules or
profiles, so instead of depending on `:duct/logger`, for example, you
might depend on `:duct.module/logging`.

You can also use `ig/refset` to make an optional dependency. If a
module exists, ensure that your module comes after it - otherwise
order as normal.

## 0.10 from 0.9

Update the Duct dependencies in your project file:

```clojure
[duct/core "0.6.1"]
[duct/module.logging "0.3.0"]
[duct/module.web "0.6.0"]
[duct/module.ataraxy "0.2.0"]
[duct/module.cljs "0.3.0"]
[duct/module.sql "0.3.0"]
```

Update the `duct/lein-duct` plugin version:

```clojure
[duct/lein-duct "0.10.0"]
```

Add the `duct.core/load-hierarchy` function to `main.clj` and
`dev.clj`. This should be a top-level form placed just below the `ns`
declaraton:

```clojure
(duct/load-hierarchy)
```

Change the `-main` function in `main.clj` to include a `prep` step. It
should look something like:

```clojure
(defn -main [& args]
  (let [keys (or (duct/parse-keys args) [:duct/daemon])]
    (-> (duct/read-config (io/resource "foo/config.edn"))
        (duct/prep keys)
        (duct/exec keys))))
```

Add a require for `duct.core.repl` in `dev.clj`:

```clojure
[duct.core.repl :as duct-repl]
```
