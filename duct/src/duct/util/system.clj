(ns duct.util.system
  (:require [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]))

(defn- add-components [system components]
  (reduce-kv (fn [m k v] (assoc m k ((:constructor v) (:options v)))) system components))

(defn- add-endpoints [system endpoints]
  (reduce-kv (fn [m k v] (assoc m k (endpoint-component v))) system endpoints))

(defn build [{:keys [system/components
                     system/endpoints
                     system/dependencies]}]
  (-> (component/system-map)
      (add-components components)
      (add-endpoints endpoints)
      (component/system-using dependencies)))
