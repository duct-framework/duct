(ns duct.component.handler
  (:require [com.stuartsierra.component :as component]
            [compojure.core :as compojure]))

(defn- find-endpoint-keys [component]
  (sort (map key (filter (comp :routes val) component))))

(defn- find-routes [component]
  (map #(:routes (get component %))
       (:endpoints component (find-endpoint-keys component))))

(defn- middleware-fn [component middleware]
  (if (vector? middleware)
    (let [[f & keys] middleware
          arguments  (map #(get component %) keys)]
      #(apply f % arguments))
    middleware))

(defn- compose-middleware [{:keys [middleware] :as component}]
  (->> (reverse middleware)
       (map #(middleware-fn component %))
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
