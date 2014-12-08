(ns {{namespace}}.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [{{namespace}}.system :refer [new-system]]))

(def config
  {:http {:port (some-> env :port Integer.)}
   :app  {:middleware     [[wrap-hide-errors :internal-error]]
          :internal-error {{^site?}}"Internal Server Error"{{/site?}}{{#site?}}(io/resource "errors/500.html"){{/site?}}}})

(defn -main [& args]
  (let [system (new-system config)]
    (println "Starting HTTP server on port" (-> system :http :port))
    (component/start system)))
