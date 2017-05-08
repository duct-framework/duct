(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [duct.core :as duct]{{#cljs?}}
            [duct.repl.figwheel :refer [cljs-repl]]{{/cljs?}}
            [eftest.runner :as eftest]
            [integrant.core :as ig]
            [integrant.repl :refer [clear halt go init prep reset]]
            [integrant.repl.state :refer [config system]]))

(defn read-config []
  (duct/read-config
   (io/resource "dev.edn")
   (io/resource "local.edn")))

(defn test []
  (eftest/run-tests (eftest/find-tests "test")))

(when (io/resource "local.clj")
  (load "local"))

(integrant.repl/set-prep! (comp duct/prep read-config))
