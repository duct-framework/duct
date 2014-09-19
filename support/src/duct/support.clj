(ns duct.support
  (:require [com.stuartsierra.component :as component]
            [ring.util.response :as response]
            [taoensso.timbre :as log]))

(defn html-resource [resource]
  (-> (response/resource-response resource)
      (response/content-type "text/html")))

(defn wrap-log-errors [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable t
        (log/error t)
        (throw t)))))

(defn wrap-hide-errors
  ([handler]
     (wrap-hide-errors handler "public/500.html"))
  ([handler error-resource]
     (fn [request]
       (try
         (handler request)
         (catch Throwable _
           (-> (html-resource error-resource)
               (response/status 500)))))))

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
