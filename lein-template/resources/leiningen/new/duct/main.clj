(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [{{namespace}}.system :refer [new-system]]))

(def config
  {:http {:port (Integer. (env :port "3000"))}
   :app  {:middleware     [[wrap-hide-errors :internal-error]]
          :internal-error "errors/500.html"}})

(defn -main [& args]
  (println "Starting HTTP server on port" (-> config :http :port))
  (component/start (new-system config)))
