(ns duct.component.routes
  (:require [com.stuartsierra.component :as component]))

(defrecord RoutesComponent [build-routes]
  component/Lifecycle
  (start [component]
    (if (:routes component)
      component
      (assoc component :routes (build-routes component))))
  (stop [component]
    (dissoc component key)))

(defn routes-component [build-routes]
  (->RoutesComponent build-routes))
