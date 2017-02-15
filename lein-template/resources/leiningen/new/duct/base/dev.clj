(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [duct.core :as duct]
            [duct.repl :refer [setup test]]
            [integrant.core :as ig]
            [integrant.repl :refer [clear config halt go init prep reset system]]))

(defn read-config []
  (duct/read-config
   (io/resource "{{dirs}}/config.edn")
   (io/resource "dev.edn")
   (io/resource "local.edn")))

(integrant.repl/set-prep! (comp duct/prep read-config))
