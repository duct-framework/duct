(ns duct.util.system
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.util.config :as config]
            [duct.util.namespace :as ns]
            [meta-merge.core :refer [meta-merge]]))

(defn- add-components [system components config]
  (reduce-kv (fn [m k v] (assoc m k ((ns/load-var v) (config k)))) system components))

(defn- add-endpoints [system endpoints]
  (reduce-kv (fn [m k v] (assoc m k (endpoint-component (ns/load-var v)))) system endpoints))

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
  ([sources options]
   (->> sources
        (map #(config/read (io/resource %) options))
        (apply meta-merge)
        (build-system))))
