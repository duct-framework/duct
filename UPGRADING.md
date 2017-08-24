# Upgrading

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
