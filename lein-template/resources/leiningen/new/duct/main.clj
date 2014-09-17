(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [{{namespace}}.system :refer [new-system]]))

(def config
  {:http {:port (Integer. (env :port "3000"))}
   :app  {:middleware []}})

(defn -main [& args]
  (println "Starting HTTP server on port" (-> config :http :port))
  (component/start (new-system config)))
