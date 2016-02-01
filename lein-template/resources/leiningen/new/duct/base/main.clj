(ns {{namespace}}.main
  (:gen-class)
  (:require {{#site?}}[clojure.java.io :as io]
            {{/site?}}[com.stuartsierra.component :as component]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [duct.util.runtime :refer [add-shutdown-hook]]
            [meta-merge.core :refer [meta-merge]]
            [{{namespace}}.config :as config]
            [{{namespace}}.system :refer [new-system]]))

(def prod-config
  {:app {:middleware     [[wrap-hide-errors :internal-error]]
         :internal-error {{^site?}}"Internal Server Error"{{/site?}}{{#site?}}(io/resource "errors/500.html"){{/site?}}}})

(def config
  (meta-merge config/defaults
              config/environ
              prod-config))

(defn -main [& args]
  (let [system (new-system config)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (add-shutdown-hook ::stop-system #(component/stop system))
    (component/start system)))
