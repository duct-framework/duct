(ns {{namespace}}.main
    (:gen-class)
    (:require [com.stuartsierra.component :as component]
              [clojure.java.io :as io]
              [duct.util.runtime :refer [add-shutdown-hook]]
              [duct.util.system :refer [load-system]]
              [environ.core :refer [env]]
              [taoensso.timbre :as log]{{#jdbc?}}{{#heroku?}}
              [hanami.core :as hanami]{{/heroku?}}{{/jdbc?}}))

(defn -main [& args]
  (let [bindings {'http-port (Integer/parseInt (:port env "3000")){{#jdbc?}}
                  'db-uri    {{^heroku?}}(:database-url env){{/heroku?}}{{#heroku?}}(hanami/jdbc-uri (:database-url env)){{/heroku?}}{{/jdbc?}}}
        system   (->> (load-system [(io/resource "{{dirs}}/system.edn")] bindings)
                      (component/start))]
    (add-shutdown-hook ::stop-system #(component/stop system))
    (log/info "Started HTTP server on port" (-> system :http :port))))
