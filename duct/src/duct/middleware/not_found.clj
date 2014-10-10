(ns duct.middleware.not-found
  (:require [ring.util.response :as response]))

(defn wrap-not-found
  ([handler]
     (wrap-not-found handler "public/404.html"))
  ([handler not-found-resource]
     (fn [request]
       (or (handler request)
           (-> (response/resource-response not-found-resource)
               (response/content-type "text/html")
               (response/status 404))))))
