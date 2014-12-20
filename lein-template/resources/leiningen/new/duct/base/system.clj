(ns {{namespace}}.system
  (:require {{#site?}}[clojure.java.io :as io]
            {{/site?}}[com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]{{#jdbc?}}
            [duct.component.hikaricp :refer [hikaricp]]{{/jdbc?}}
            [duct.middleware.not-found :refer [wrap-not-found]]
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults {{defaults}}]]{{#site?}}
            [ring.middleware.webjars :refer [wrap-webjars]]{{/site?}}{{#example?}}
            [{{namespace}}.endpoint.example :refer [example-endpoint]]{{/example?}}))

(def base-config
  {:app {:middleware [[wrap-not-found :not-found]{{#site?}}
                      [wrap-webjars]{{/site?}}
                      [wrap-defaults :defaults]]
         :not-found  {{^site?}}"Resource Not Found"{{/site?}}{{#site?}}(io/resource "errors/404.html"){{/site?}}
         :defaults   {{defaults}}}})

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
         :app  (handler-component (:app config))
         :http (jetty-server (:http config)){{#jdbc?}}
         :db   (hikaricp (:db config)){{/jdbc?}}{{#example?}}
         :example (endpoint-component example-endpoint){{/example?}})
        (component/system-using
         {:http [:app]
          :app  [{{#example?}}:example{{/example?}}]{{#example?}}
          :example [{{#jdbc?}}:db{{/jdbc?}}]{{/example?}}}))))
