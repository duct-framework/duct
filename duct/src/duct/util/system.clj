(ns duct.util.system
  (:require [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.util.namespace :as ns]))

(defn- add-components [system components config]
  (reduce-kv (fn [m k v] (assoc m k ((ns/resolve-var v) (config k)))) system components))

(defn- add-endpoints [system endpoints]
  (reduce-kv (fn [m k v] (assoc m k (endpoint-component (ns/resolve-var v))))
             system
             endpoints))

(defn- dissoc-all [m ks]
  (apply dissoc m ks))

(defn build [{:keys [components endpoints dependencies config]}]
  (-> (component/system-map)
      (add-components components config)
      (add-endpoints endpoints)
      (component/system-using dependencies)
      (into (-> config
                (dissoc-all (keys components))
                (dissoc-all (keys endpoints))))))
