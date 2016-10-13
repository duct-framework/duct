(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.generate :as gen]
            [duct.util.config :as config]
            [duct.util.logging :as logging]
            [duct.util.repl :refer [setup test cljs-repl migrate rollback]]
            [duct.util.system :as system]
            [reloaded.repl :refer [system init start stop go reset]]
            [taoensso.timbre :as log]))

(defn read-config []
  (->> ["{{dirs}}/config.edn" "dev.edn" "local.edn"]
       (keep io/resource)
       (apply config/read)))

(defn load-system []
  (let [config (read-config)]
    (logging/set-config! config)
    (system/build config)))

(when (io/resource "local.clj")
  (load "local"))

(gen/set-ns-prefix '{{namespace}})

(logging/set-config! (read-config))

(reloaded.repl/set-init! load-system)
