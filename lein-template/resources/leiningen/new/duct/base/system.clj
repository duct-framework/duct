(ns {{namespace}}.system
  (:require [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]
            [ring.component.jetty :refer [jetty-server]]{{#example?}}
            [{{namespace}}.endpoint.example :refer [example-endpoint]]{{/example?}}))

(defn new-system [config]
  (-> (component/system-map
       :app  (handler-component (:app config))
       :http (jetty-server (:http config)){{#example?}}
       :example (endpoint-component example-endpoint){{/example?}})
      (component/system-using
       {:http [:app]
        :app  [{{#example?}}:example{{/example?}}]})))
