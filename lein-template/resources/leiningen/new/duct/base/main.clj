(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [duct.util.runtime :refer [add-shutdown-hook]]
            [duct.util.system :refer [build-system]]))

(defn -main [& args]
  (let [system (build-system "{{dirs}}/system.edn"
                             "{{dirs}}/config.edn"
                             {:profile :prod})]
    (println "Starting HTTP server on port" (-> system :http :port))
    (add-shutdown-hook ::stop-system #(component/stop system))
    (component/start system)))
