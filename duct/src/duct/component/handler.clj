(ns duct.component.handler
  (:require [com.stuartsierra.component :as component]
            [compojure.core :as compojure]
            [duct.util.namespace :as ns]))

(defn- find-endpoint-keys [component]
  (sort (map key (filter (comp :routes val) component))))

(defn- find-routes [component]
  (map #(:routes (get component %))
       (:endpoints component (find-endpoint-keys component))))

(defn- make-middleware [{function :fn, args :args}]
  #(apply function % args))

(defn- comp-middleware [{:keys [middleware]}]
  (->> middleware (map make-middleware) (reverse) (apply comp)))

(defrecord Handler [middleware]
  component/Lifecycle
  (start [component]
    (if-not (:handler component)
      (let [routes     (find-routes component)
            middleware (comp-middleware component)
            handler    (middleware (apply compojure/routes routes))]
        (assoc component :handler handler))
      component))
  (stop [component]
    (dissoc component :handler)))

(defn handler-component [options]
  (map->Handler options))
