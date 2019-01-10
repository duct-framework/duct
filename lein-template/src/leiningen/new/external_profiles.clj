(ns leiningen.new.external-profiles
  (:require [clojure.string]
            [hydrogen.example1-duct-profile]))

(defn eval-profile [namespace]
  (prn "NAMESPACE: " namespace)
  (prn "SYMBOL: " (symbol
                    namespace
                    "main"))
  ((eval (symbol
           namespace
           "main"))
    {:project-name "foobar"}))

(defn handle-profile [profile]
  (->
    (str (namespace profile) "." (name profile) "-duct-profile")
    eval-profile))

(defn extra-deps [profiles]
  (map handle-profile profiles))