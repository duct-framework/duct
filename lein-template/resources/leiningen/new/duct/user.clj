(ns user
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [reloaded.repl :refer [system init start stop go reset]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace]]
            [{{namespace}}.system :as system]))

(def config
  {:http {:port 3000}
   :app  {:middleware [wrap-stacktrace]}})

(when (io/resource "local.clj")
  (load "local"))

(reloaded.repl/set-init! #(system/new-system config))
