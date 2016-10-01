(ns duct.util.system
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.walk :as walk]
            [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
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

(defn- add-components [system components config]
  (reduce-kv (fn [m k v] (assoc m k ((ns/resolve-var v) (config k)))) system components))

(defn- add-endpoints [system endpoints]
  (reduce-kv (fn [m k v] (assoc m k (endpoint-component (ns/resolve-var v))))
             system
             endpoints))

(defn- dissoc-all [m ks]
  (apply dissoc m ks))

(defn build-system [{:keys [components endpoints dependencies config]}]
  (-> (component/system-map)
      (add-components components config)
      (add-endpoints endpoints)
      (component/system-using dependencies)
      (into (-> config
                (dissoc-all (keys components))
                (dissoc-all (keys endpoints))))))

(defn load-system
  ([sources]
   (load-system sources {}))
  ([sources bindings]
   (->> sources
        (map #(read-config % bindings))
        (apply meta-merge)
        (build-system))))
