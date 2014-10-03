(ns duct.component.handler
  (:require [com.stuartsierra.component :as component]))

(defrecord Handler [build-handler middleware]
  component/Lifecycle
  (start [component]
    (if-not (:handler component)
      (let [wrap-middleware (apply comp identity (reverse middleware))]
        (assoc component :handler (wrap-middleware (build-handler component))))
      component))
  (stop [component]
    (dissoc component :handler)))

(defn handler-component [options]
  (map->Handler options))
