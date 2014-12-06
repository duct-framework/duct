(ns duct.middleware.not-found
  (:require [compojure.response :as compojure]
            [ring.util.response :as response]))

(defn wrap-not-found
  [handler error-response]
  (fn [request]
    (or (handler request)
        (-> (compojure/render error-response request)
            (response/content-type "text/html")
            (response/status 404)))))
