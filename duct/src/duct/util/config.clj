(ns duct.util.config
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [duct.util.namespace :as ns]
            [meta-merge.core :refer [meta-merge]]))

(defmulti reader
  (fn [tag value] tag))

(defmethod reader 'resource [_ value]
  (io/resource value))

(defmethod reader 'var [_ value]
  (ns/load-var value))

(defn read-config [source bindings]
  (->> (slurp source)
       (edn/read-string {:default reader})
       (walk/postwalk #(bindings % %))))

(defn read-and-merge-configs [sources bindings]
  (->> sources
       (map #(read-config % bindings))
       (apply meta-merge)))
