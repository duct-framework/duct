(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.generate :as gen]
            [duct.util.config :as config]
            [duct.util.repl :refer [setup test cljs-repl migrate rollback]]
            [duct.util.system :as system]
            [reloaded.repl :refer [system init start stop go reset]]))

(defn build-system []
  (->> ["{{dirs}}/system.edn" "dev.edn" "local.edn"]
       (keep io/resource)
       (apply config/read)
       (system/build)))

(when (io/resource "local.clj")
  (load "local"))

(gen/set-ns-prefix '{{namespace}})

(reloaded.repl/set-init! build-system)
