(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [duct.middleware.logging :refer [wrap-log-requests wrap-log-errors]]
            [{{namespace}}.system :refer [new-system]]))

(def config
  {:http {:port (Integer. (env :port "3000"))}
   :app  {:middleware [wrap-log-requests
                       wrap-log-errors
                       wrap-hide-errors]}})

(defn -main [& args]
  (println "Starting HTTP server on port" (-> config :http :port))
  (component/start (new-system config)))
