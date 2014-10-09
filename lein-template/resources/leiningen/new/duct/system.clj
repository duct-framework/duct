(ns {{namespace}}.system
  (:require [com.stuartsierra.component :as component]
            [duct.component.handler :refer [handler-component]]
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :as defaults]
            [{{namespace}}.component.example :as example]))

(def base-config
  {:http {:port 3000}
   :app  {:middleware [[defaults/wrap-defaults :defaults]]
          :defaults   defaults/site-defaults}})

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
         :app     (handler-component (:app config))
         :http    (jetty-server (:http config))
         :example example/component)
        (component/system-using
         {:http [:app]
          :app  [:example]}))))
