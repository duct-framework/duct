(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [{{namespace}}.system :refer [new-system]]))

(def config
  {:http {:port (some-> env :port Integer.)}
   :app  {:middleware     [[wrap-hide-errors :internal-error]]
          :internal-error "errors/500.html"}})

(defn -main [& args]
  (let [system (new-system config)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (component/start system)))
