(ns user
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [meta-merge.core :refer [meta-merge]]
            [reloaded.repl :refer [system init start stop go reset]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace]]{{#ragtime?}}
            [duct.component.ragtime :as ragtime]{{/ragtime?}}
            [{{namespace}}.config :as config]
            [{{namespace}}.system :as system]))

(def dev-config
  {:app {:middleware [wrap-stacktrace]}})

(def config
  (meta-merge config/defaults
              config/environ
              dev-config))

{{#ragtime?}}
(defn migrate []
  (-> system :ragtime ragtime/reload ragtime/migrate))

(defn rollback
  ([]  (rollback 1))
  ([x] (-> system :ragtime ragtime/reload (ragtime/rollback x))))
{{/ragtime?}}

(when (io/resource "local.clj")
  (load "local"))

(reloaded.repl/set-init! #(system/new-system config))
