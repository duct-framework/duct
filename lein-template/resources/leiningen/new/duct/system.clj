(ns {{namespace}}.system
  (:require [com.stuartsierra.component :as component]
            [ring.component.jetty :refer [jetty-server]]
            [{{namespace}}.handler :refer [new-handler]]))

(defrecord Application []
  component/Lifecycle
  (start [component]
    (if-not (:handler component)
      (assoc component :handler (new-handler component))
      component))
  (stop [component]
    (dissoc component :handler)))

(def default-config
  {:app  {}
   :http {:port 3000}})

(defn new-system [config]
  (let [config (merge default-config config)]
    (component/system-map
     :app  (map->Application (:app config))
     :http (-> (jetty-server (:http config))
               (component/using [:app])))))
