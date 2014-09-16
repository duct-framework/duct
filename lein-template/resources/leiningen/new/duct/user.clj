(ns user
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [reloaded.repl :refer [system init start stop go reset]]
            [ring.middleware.stacktrace :as ring-stacktrace]
            [{{namespace}}.system :as system]))

(defn new-handler [config]
  (-> ((-> system/base-config :app :new-handler) config)
      (ring-stacktrace/wrap-stacktrace)))

(def config
  {:app  {:new-handler new-handler}
   :http {:port 3000}})

(when (io/resource "local.clj")
  (load "local"))

(reloaded.repl/set-init! #(system/new-system config))
