(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [duct.generate :as gen]
            [duct.util.config :as config]
            [duct.util.repl :refer [setup test cljs-repl migrate rollback]]
            [integrant.core :as ig]
            [integrant.repl :refer [config system prep init halt go reset]]
            [taoensso.timbre :as log]))

(defn read-config []
  (->> ["{{dirs}}/config.edn" "dev.edn" "local.edn"]
       (keep io/resource)
       (apply config/read)))

(when (io/resource "local.clj")
  (load "local"))

(gen/set-ns-prefix '{{namespace}})

(integrant.repl/set-prep! read-config)
