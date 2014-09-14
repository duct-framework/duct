(ns duct.support
  (:require [com.stuartsierra.component :as component]))

(defrecord Application [new-handler]
  component/Lifecycle
  (start [component]
    (if-not (:handler component)
      (assoc component :handler (new-handler component))
      component))
  (stop [component]
    (dissoc component :handler)))

(defn app-component [new-handler options]
  (-> (->Application new-handler)
      (into options)))
