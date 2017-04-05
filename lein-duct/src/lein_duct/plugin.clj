(ns lein-duct.plugin
  (:require [clojure.walk :as walk]
            [leiningen.core.project :as p]))

(defn- add-target-path-property [{:keys [target-path] :as project}]
  (update project :jvm-opts (fnil conj []) (str "-Dduct.target.path=" target-path)))

(defn- ^java.nio.file.Path ->Path [s]
  (java.nio.file.Paths/get s (make-array String 0)))

(defn- relativize [root path]
  (str (.relativize (->Path root) (->Path path))))

(defn- relative-target-path [{:keys [root target-path]}]
  (relativize root target-path))

(defn- format-all [x & args]
  (walk/postwalk #(if (string? %) (apply format % args) %) x))

(defn- format-with-target-path [project key]
  (update project key format-all (relative-target-path project)))

(defn middleware [project]
  (-> project
      (add-target-path-property)
      (format-with-target-path :resource-paths)))
