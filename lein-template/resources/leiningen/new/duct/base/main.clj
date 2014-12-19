(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [meta-merge.core :refer [meta-merge]]
            [{{namespace}}.config :as config]
            [{{namespace}}.system :refer [new-system]]))

(def config
  (meta-merge config/base
              config/environ
              config/production))

(defn -main [& args]
  (let [system (new-system config)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (component/start system)))
