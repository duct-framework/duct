(ns user
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [reloaded.repl :refer [system init start stop go reset]]
            [{{namespace}}.system :refer [new-system]]))

(def config
  {:http {:port 3000}})

(reloaded.repl/set-init! #(new-system config))
