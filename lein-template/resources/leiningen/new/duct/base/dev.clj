(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.generate :as gen]
            [duct.util.system :refer [load-system]]
            [reloaded.repl :refer [system init start stop go reset]]
            [dev.tasks :refer :all]))

(defn new-system []
  (load-system ["{{dirs}}/system.edn" "dev.edn"]))

(when (io/resource "local.clj")
  (load "local"))

(gen/set-ns-prefix '{{namespace}})

(reloaded.repl/set-init! new-system)
