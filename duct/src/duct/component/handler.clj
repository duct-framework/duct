(ns duct.component.handler
  (:require [com.stuartsierra.component :as component]
            [compojure.core :as compojure]
            [duct.util.namespace :as ns]))

(defn- find-endpoint-keys [component]
  (sort (map key (filter (comp :routes val) component))))

(defn- find-routes [component]
  (map #(:routes (get component %))
       (:endpoints component (find-endpoint-keys component))))

(defn- middleware-fn [middleware]
  (if-not (vector? middleware)
    (recur [middleware])
    (let [[f & args] middleware
          func       (cond-> f (symbol? f) ns/load-var)]
      #(apply func % args))))

(defn- compose-middleware [{:keys [middleware] :as component}]
  (->> (reverse middleware)
       (map middleware-fn)
       (apply comp identity)))

(defrecord Handler [middleware]
  component/Lifecycle
  (start [component]
    (if-not (:handler component)
      (let [routes  (find-routes component)
            wrap-mw (compose-middleware component)
            handler (wrap-mw (apply compojure/routes routes))]
        (assoc component :handler handler))
      component))
  (stop [component]
    (dissoc component :handler)))

(defn handler-component [options]
  (map->Handler options))
