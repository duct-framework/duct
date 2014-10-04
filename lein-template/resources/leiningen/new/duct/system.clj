(ns {{namespace}}.system
  (:require [com.stuartsierra.component :as component]
            [ring.component.jetty :refer [jetty-server]]
            [duct.component.handler :refer [handler-component]]
            [{{namespace}}.handler :refer [new-handler]]))

(def base-config
  {:http {:port 3000}
   :app  {:build-handler new-handler
          :middleware []}})

(defn new-system [config]
  (let [config (merge-with merge base-config config)]
    (-> (component/system-map
         :app  (handler-component (:app config))
         :http (jetty-server (:http config)))
        (component/system-using
         {:http [:app]}))))
