(ns duct.middleware.logging
  (:require [ring.util.request :as request]
            [ring.util.response :as response]
            [taoensso.timbre :as log]))

(defn log-request [request response]
  {:request
   {:remote-addr    (:remote-addr request)
    :request-method (:request-method request)
    :url            (request/request-url request)}
   :response
   {:status         (:status response)
    :content-length (response/get-header response "Content-Length")}})

(defn wrap-log-requests [handler]
  (fn [request]
    (let [response (handler request)]
      (log/info (log-request request response))
      response)))

(defn wrap-log-errors [handler]
  (fn [request]
    (try
      (handler request)
      (catch Throwable t
        (log/error t)
        (throw t)))))
