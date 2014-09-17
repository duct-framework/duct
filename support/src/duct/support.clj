(ns duct.support
  (:require [com.stuartsierra.component :as component]
            [ring.util.response :as response]))

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

(defn wrap-hide-errors
  ([handler]
     (wrap-hide-errors handler "public/500.html"))
  ([handler error-resource]
     (fn [request]
       (try
         (handler request)
         (catch Throwable _
           (-> (response/resource-response error-resource)
               (assoc :status 500)))))))
