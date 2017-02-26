(ns lein-duct.plugin
  (:require [leiningen.core.project :as p]))

(defn- add-target-path-property [{:keys [target-path] :as project}]
  (update project :jvm-opts (fnil conj []) (str "-Dduct.target.path=" target-path)))

(defn middleware [project]
  (add-target-path-property project))
