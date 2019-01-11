(ns leiningen.new.external-profiles
  (:require [clojure.string]
            [hydrogen.example1-duct-profile]))

(defn eval-profile [namespace project-data]
  (prn "NAMESPACE: " namespace)
  (prn "SYMBOL: " (symbol
                    namespace
                    "main"))
  ((eval (symbol
           namespace
           "main"))
    project-data))

(defn handle-profile [profile project-data]
  (->
    (str (namespace profile) "." (name profile) "-duct-profile")
    (eval-profile project-data)))

(defn main [profiles project-data]
  (->> (map #(handle-profile % project-data) profiles)
       (apply merge-with into)))