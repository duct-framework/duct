(ns {{namespace}}.main
    (:gen-class)
    (:require [com.stuartsierra.component :as component]
              [duct.util.runtime :refer [add-shutdown-hook]]
              [duct.util.system :refer [load-system]]
              [environ.core :refer [env]]
              [clojure.java.io :as io]))

(def env-bindings
  {'http-port (Integer/parseInt (:port env "3000")){{#jdbc?}}
   'db-uri    (:database-url env){{/jdbc?}}})

(defn -main [& args]
  (let [system (load-system [(io/resource "{{dirs}}/system.edn")] env-bindings)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (add-shutdown-hook ::stop-system #(component/stop system))
    (component/start system)))
