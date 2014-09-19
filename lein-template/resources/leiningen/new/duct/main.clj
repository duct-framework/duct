(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [duct.support :as support]
            [taoensso.timbre :as log]
            [ring.util.request :as request]
            [{{namespace}}.system :refer [new-system]]))

(defn wrap-logging [handler]
  (fn [request]
    (let [response (handler request)]
      (log/info {:request
                 {:client (:remote-addr request)
                  :method (:request-method request)
                  :url    (request/request-url request)}
                 :response
                 {:status (:status response)}})
      response)))

(def config
  {:http {:port (Integer. (env :port "3000"))}
   :app  {:middleware [wrap-logging
                       support/wrap-log-errors
                       support/wrap-hide-errors]}})

(defn -main [& args]
  (println "Starting HTTP server on port" (-> config :http :port))
  (component/start (new-system config)))
