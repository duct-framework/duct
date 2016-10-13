(ns duct.util.config
  (:refer-clojure :exclude [read])
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

(defn read
  ([source]
   (edn/read-string {:default reader} (slurp source)))
  ([source & sources]
   (apply meta-merge (read source) (map read sources))))

(defn bind [config bindings]
  (walk/postwalk #(bindings % %) config))
