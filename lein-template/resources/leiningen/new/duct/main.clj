(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [duct.support :as support]
            [taoensso.timbre :as log]
            [{{namespace}}.system :refer [new-system]]))

(def config
  {:http {:port (Integer. (env :port "3000"))}
   :app  {:middleware [support/wrap-log-requests
                       support/wrap-log-errors
                       support/wrap-hide-errors]}})

(defn -main [& args]
  (println "Starting HTTP server on port" (-> config :http :port))
  (component/start (new-system config)))
