(ns {{namespace}}.main
    (:gen-class)
    (:require [com.stuartsierra.component :as component]
              [clojure.java.io :as io]
              [duct.util.config :as config]
              [duct.util.logging :as logging]
              [duct.util.runtime :refer [add-shutdown-hook]]
              [duct.util.system :as system]
              [environ.core :refer [env]]
              [taoensso.timbre :as log]{{#jdbc?}}{{#heroku?}}
              [hanami.core :as hanami]{{/heroku?}}{{/jdbc?}}))

(defn- read-config []
  (-> (config/read (io/resource "{{dirs}}/config.edn"))
      (config/bind {'http-port (Integer/parseInt (:port env "3000")){{#jdbc?}}
                    'db-uri    {{^heroku?}}(:database-url env){{/heroku?}}{{#heroku?}}(hanami/jdbc-uri (:database-url env)){{/heroku?}}{{/jdbc?}}})))

(defn- run-system [config]
  (let [system (->> config system/build component/start)]
    (add-shutdown-hook ::stop-system #(component/stop system))
    (log/info "Started HTTP server on port" (-> system :http :port))))

(defn -main [& args]
  (doto (read-config)
    (logging/set-config!)
    (run-system)))
