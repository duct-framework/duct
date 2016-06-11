(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.generate :as gen]
            [duct.util.system :refer [build-system]]
            [reloaded.repl :refer [system init start stop go reset]]
            [dev.tasks :refer :all]))

(defn new-system []
  (build-system "{{dirs}}/system.edn"
                "{{dirs}}/config.edn"
                {:profile :dev}))

(when (io/resource "dev/local.clj")
  (load "dev/local"))

(gen/set-ns-prefix '{{namespace}})

(reloaded.repl/set-init! new-system)
