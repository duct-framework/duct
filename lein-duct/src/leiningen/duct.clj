(ns leiningen.duct
  (:refer-clojure :exclude [compile])
  (:require [clojure.java.io :as io]
            [leiningen.core.main :as main]
            [leiningen.core.eval :as eval]))

(defn- copy-resource [resource path]
  (io/copy (io/input-stream (io/resource resource)) (io/file path))
  (println "Created" path))

(defn setup
  "Add local project files."
  [project]
  (copy-resource "leiningen/duct/profiles.clj" "profiles.clj")
  (copy-resource "leiningen/duct/dir-locals.el" ".dir-locals.el")
  (copy-resource "leiningen/duct/local.edn" "dev/resources/local.edn")
  (copy-resource "leiningen/duct/local.clj" "dev/src/clj/local.clj"))

(defn duct
  "Tasks for managing a Duct project."
  {:subtasks [#'setup]}
  [project subtask & args]
  (case subtask
    "setup"   (apply setup project args)
    (main/abort "Unknown duct subtask:" subtask)))
