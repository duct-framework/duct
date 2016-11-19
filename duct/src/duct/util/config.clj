(ns duct.util.config
  (:refer-clojure :exclude [read])
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [duct.util.namespace :as ns]
            [medley.core :as m]
            [meta-merge.core :refer [meta-merge]]))

(defmulti reader
  (fn [tag value] tag))

(defmethod reader 'resource [_ value]
  (io/resource value))

(defmethod reader 'var [_ value]
  (ns/load-var value))

(defn- replace-namespaced-keywords [config]
  (letfn [(walk-and-replace [subconfig]
            (walk/postwalk
             #(if (and (keyword? %) (namespace %))
                (walk-and-replace (config % %))
                %)
             subconfig))]
    (m/map-vals walk-and-replace config)))

(defn read* [source]
  (edn/read-string {:default reader} (slurp source)))

(defn read [& sources]
  (->> (map read* sources)
       (apply meta-merge)
       (replace-namespaced-keywords)))

(defn bind [config bindings]
  (walk/postwalk #(bindings % %) config))

(defn apply-fn [fn-config]
  (apply (:fn fn-config) (:args fn-config)))
