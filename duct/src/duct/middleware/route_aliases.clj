(ns duct.middleware.route-aliases
  (:require [ring.util.request :refer [set-context]]))

(defn- update-context [request]
  (if-let [context (:context request)]
    (set-context request context)
    request))

(defn wrap-route-aliases [handler aliases]
  (fn [request]
    (if-let [alias (aliases (:uri request))]
      (handler (update-context (assoc request :uri alias)))
      (handler request))))
