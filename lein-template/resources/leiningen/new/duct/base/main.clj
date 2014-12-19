(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [meta-merge.core :refer [meta-merge]]
            [{{namespace}}.config :as config]
            [{{namespace}}.system :refer [new-system]]))

(def main-config
  (meta-merge config/defaults
              config/environ
              config/production))

(defn -main [& args]
  (let [system (new-system main-config)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (component/start system)))
