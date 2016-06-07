(ns duct.util.config
  (:require [clojure.edn :as edn]
            [clojure.walk :as walk]
            [environ.core :refer [env]]))

(defrecord Ref [keys])

(defmulti reader
  (fn [options tag value] tag))

(defmethod reader 'ref [_ _ value]
  (->Ref value))

(defmethod reader 'import [{:keys [imports]} _ value]
  (get-in imports value))

(defn- resolve-refs-once [config]
  (walk/postwalk #(if (instance? Ref %) (get-in config (:keys %)) %) config))

(defn- resolve-refs [config]
  (let [config' (resolve-refs-once config)]
    (if (= config config') config (recur config'))))

(def default-options
  {:imports {:env env}})

(defn read-config
  ([source]
   (read-config source {}))
  ([source options]
   (let [options (merge default-options options)]
     (->> (slurp source)
          (edn/read-string {:default (partial reader options)})
          (resolve-refs)))))
