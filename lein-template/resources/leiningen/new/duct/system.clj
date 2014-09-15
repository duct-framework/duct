(ns {{namespace}}.system
  (:require [com.stuartsierra.component :as component]
            [ring.component.jetty :refer [jetty-server]]
            [duct.support :as support]
            [{{namespace}}.handler :refer [new-handler]]))

(def default-config
  {:app  {:new-handler new-handler}
   :http {:port 3000}})

(defn new-system [config]
  (let [config (merge default-config config)]
    (component/system-map
     :app  (support/app-component (:app config))
     :http (-> (jetty-server (:http config))
               (component/using [:app])))))
