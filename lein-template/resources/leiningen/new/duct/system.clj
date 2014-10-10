(ns {{namespace}}.system
  (:require [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]
            [duct.middleware.not-found :refer [wrap-not-found]]
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [{{namespace}}.endpoint.example :refer [example-endpoint]]))

(def base-config
  {:http {:port 3000}
   :app  {:middleware [[wrap-not-found :not-found]
                       [wrap-defaults :defaults]]
          :not-found  "errors/404.html"
          :defaults   site-defaults}})

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
         :app     (handler-component (:app config))
         :http    (jetty-server (:http config))
         :example (endpoint-component example-endpoint))
        (component/system-using
         {:http [:app]
          :app  [:example]}))))
