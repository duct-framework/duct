(ns duct.util.config
  (:refer-clojure :exclude [read resolve])
  (:require [clojure.edn :as edn]
            [clojure.walk :as walk]
            [environ.core :refer [env]]
            [meta-merge.core :refer [meta-merge]]))

(defprotocol Resolvable
  (resolve [x config]))

(defrecord Ref [keys]
  Resolvable
  (resolve [_ config] (get-in config keys)))

(defrecord Merge [values]
  Resolvable
  (resolve [_ _] (apply meta-merge values)))

(defrecord Or [values]
  Resolvable
  (resolve [_ _] (some identity values)))

(defn- resolve-once [config]
  (walk/postwalk #(if (satisfies? Resolvable %) (resolve % config) %) config))

(defn- resolve-recursively [config]
  (let [config' (resolve-once config)]
    (if (= config config') config (recur config'))))

(defmulti reader
  (fn [options tag value] tag))

(defmethod reader 'import [{:keys [imports]} _ value]
  (get-in imports value))

(defmethod reader 'ref [_ _ value]
  (->Ref value))

(defmethod reader 'merge [_ _ value]
  (->Merge value))

(defmethod reader 'or [_ _ value]
  (->Or value))

(def default-options
  {:imports {:env env}})

(defn read
  ([source]
   (read source {}))
  ([source options]
   (let [options (merge default-options options)]
     (->> (slurp source)
          (edn/read-string {:default (partial reader options)})
          (resolve-recursively)))))
