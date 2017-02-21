(ns leiningen.duct
  (:require [clojure.java.io :as io]
            [leiningen.core.main :as main]))

(defn- copy-resource [resource path]
  (io/copy (io/input-stream (io/resource resource)) (io/file path))
  (println "Created" path))

(defn setup [_]
  (copy-resource "leiningen/duct/profiles.clj" "profiles.clj")
  (copy-resource "leiningen/duct/local.edn" "dev/resources/local.edn")
  (copy-resource "leiningen/duct/local.clj" "dev/src/local.clj"))

(defn duct [project subtask & args]
  (case subtask
    "setup" (apply setup project args)
    (main/abort "Unknown duct subtask:" subtask)))
