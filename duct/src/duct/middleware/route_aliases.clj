(ns duct.middleware.route-aliases
  (:require [ring.util.request :as request]))

(defn wrap-route-aliases [handler aliases]
  (fn [request]
    (let [path (request/path-info request)]
      (if-let [alias (aliases path)]
        (handler (assoc request :path-info alias))
        (handler request)))))
