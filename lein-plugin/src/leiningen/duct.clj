(ns leiningen.duct
  (:refer-clojure :exclude [compile])
  (:require [clojure.java.io :as io]
            [leiningen.core.main :as main]
            [leiningen.core.eval :as eval]))

(defn- copy-resource [resource path]
  (io/copy (io/input-stream (io/resource resource)) (io/file path))
  (println "Created" path))

(defn setup [_]
  (copy-resource "leiningen/duct/profiles.clj" "profiles.clj")
  (copy-resource "leiningen/duct/local.edn" "dev/resources/local.edn")
  (copy-resource "leiningen/duct/local.clj" "dev/src/local.clj"))

(defn compile [{{:keys [configs]} :duct :as project}]
  (eval/eval-in-project
   project
   `(duct.core/compile (doto (duct.core/read-config ~@configs) prn))
   `(require 'duct.core)))

(defn duct [project subtask & args]
  (case subtask
    "setup"   (apply setup project args)
    "compile" (apply compile project args)
    (main/abort "Unknown duct subtask:" subtask)))
