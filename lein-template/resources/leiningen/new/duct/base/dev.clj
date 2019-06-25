(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [fipp.edn :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [duct.core :as duct]
            [duct.core.repl :as duct-repl]{{#cljs?}}
            [duct.repl.figwheel :refer [cljs-repl]]{{/cljs?}}
            [eftest.runner :as eftest]
            [integrant.core :as ig]
            [integrant.repl :refer [clear halt go init prep reset]]
            [integrant.repl.state :refer [config system]]))

(duct/load-hierarchy)

(defn read-config []
  (duct/read-config (io/resource "{{dirs}}/config.edn")))

(defn test []
  (eftest/run-tests (eftest/find-tests "test")))

(def profiles
  [:duct.profile/dev :duct.profile/local])

(def ^:private event->file-name
  (comp #(.getName %)
        :file))

(def ^:private matches-clojure-file?
  (partial re-matches #"^[a-zA-Z]{1}[a-zA-Z_]*(.clj|.edn)$"))

(defn- clojure-file?
  [ctx event]
  (-> event
      event->file-name
      matches-clojure-file?))

(defn- auto-reset-handler
  [ctx event]
  (binding [*ns* true]
    (ns dev)
    (reset)
    ctx))

(defn auto-reset
  "Automatically reset Duct configuration on file change.
  This affects Clojure and EDN files within [src/ resources/]."
  []
  (hawk/watch! [{:paths ["src/" "resources/"]
                 :filter clojure-file?
                 :handler auto-reset-handler}]))

(clojure.tools.namespace.repl/set-refresh-dirs "dev/src" "src" "test")

(when (io/resource "local.clj")
  (load "local"))

(integrant.repl/set-prep! #(duct/prep-config (read-config) profiles))
