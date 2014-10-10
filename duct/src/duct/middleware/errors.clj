(ns duct.middleware.errors
  (:require [ring.util.response :as response]))

(defn wrap-hide-errors [handler error-resource]
  (fn [request]
    (try
      (handler request)
      (catch Throwable _
        (-> (response/resource-response error-resource)
            (response/content-type "text/html")
            (response/status 500))))))
