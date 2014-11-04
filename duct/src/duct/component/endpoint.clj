(ns duct.component.endpoint
  (:require [com.stuartsierra.component :as component]))

(defrecord EndpointComponent [build-routes]
  component/Lifecycle
  (start [component]
    (if (:routes component)
      component
      (assoc component :routes (build-routes component))))
  (stop [component]
    (dissoc component :routes)))

(defn endpoint-component [build-routes]
  (->EndpointComponent build-routes))
