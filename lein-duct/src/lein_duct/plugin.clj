(ns lein-duct.plugin
  (:require [leiningen.core.project :as p]))

(def hierarchy-merger
  [(comp read-string slurp) (partial merge-with into) #(spit %1 (pr-str %2))])

(defn middleware [project]
  (assoc-in project [:uberjar-merge-with "duct_hierarchy.edn"] `hierarchy-merger))
