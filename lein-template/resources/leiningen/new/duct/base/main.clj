(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [duct.util.runtime :refer [add-shutdown-hook]]
            [duct.util.system :refer [load-system]]))

(defn -main [& args]
  (let [system (load-system ["{{dirs}}/system.edn"])]
    (println "Starting HTTP server on port" (-> system :http :port))
    (add-shutdown-hook ::stop-system #(component/stop system))
    (component/start system)))
