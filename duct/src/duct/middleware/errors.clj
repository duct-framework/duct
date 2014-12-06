(ns duct.middleware.errors
  (:require [compojure.response :as compojure]
            [ring.util.response :as response]))

(defn wrap-hide-errors [handler error-response]
  (fn [request]
    (try
      (handler request)
      (catch Throwable _
        (-> (compojure/render error-response request)
            (response/content-type "text/html")
            (response/status 500))))))
