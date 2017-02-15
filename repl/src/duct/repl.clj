(ns duct.repl
  (:refer-clojure :exclude [test])
  (:require [clojure.java.io :as io]
            [eftest.runner :as eftest]))

(defn- copy-resource [resource path]
  (io/copy (io/input-stream (io/resource resource)) (io/file path))
  (println "Created" path))

(defn setup []
  (copy-resource "duct/repl/profiles.clj" "profiles.clj")
  (copy-resource "duct/repl/local.edn" "dev/resources/local.edn")
  (copy-resource "duct/repl/local.clj" "dev/src/local.clj"))

(defn test []
  (eftest/run-tests (eftest/find-tests "test") {:multithread? false}))
