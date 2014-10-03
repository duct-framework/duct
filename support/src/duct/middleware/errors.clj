(ns duct.middleware.errors
  (:require [ring.util.response :as response]))

(defn html-resource [resource]
  (-> (response/resource-response resource)
      (response/content-type "text/html")))

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
