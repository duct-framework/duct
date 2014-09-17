(ns duct.support
  (:require [com.stuartsierra.component :as component]))

(defrecord Application [new-handler middleware]
  component/Lifecycle
  (start [component]
    (if-not (:handler component)
      (let [wrap-middleware (apply comp identity (reverse middleware))]
        (assoc component :handler (wrap-middleware (new-handler component))))
      component))
  (stop [component]
    (dissoc component :handler)))

(defn app-component [options]
  (map->Application options))
