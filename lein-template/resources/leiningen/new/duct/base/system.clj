{{=<< >>=}}
(ns <<namespace>>.system
  (:require <<#site?>>[clojure.java.io :as io]
            <</site?>>[com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]<<#jdbc?>>
            [duct.component.hikaricp :refer [hikaricp]]<</jdbc?>><<#ragtime?>>
            [duct.component.ragtime :refer [ragtime]]<</ragtime?>>
            [duct.middleware.not-found :refer [wrap-not-found]]<<#rest?>>
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]<</rest?>>
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults <<defaults>>]]<<#site?>>
            [ring.middleware.webjars :refer [wrap-webjars]]<</site?>><<#example?>>
            [<<namespace>>.endpoint.example :refer [example-endpoint]]<</example?>><<#rest?>>
            [<<namespace>>.endpoint.rest-example :refer [rest-endpoint]]<</rest?>>))

(def base-config
  {:app {:middleware [[wrap-not-found :not-found]<<#site?>>
                      [wrap-webjars]<</site?>><<#rest?>>
                      [wrap-json-body {:keywords? true}]
                      [wrap-json-response]<</rest?>>
                      [wrap-defaults :defaults]]
         :not-found  <<^site?>>"Resource Not Found"<</site?>><<#site?>>(io/resource "<<dirs>>/errors/404.html")<</site?>>
         :defaults   (meta-merge <<defaults>> {<<#site?>>:static {:resources "<<dirs>>/public"}<</site?>>})}<<#ragtime?>>
   :ragtime {:resource-path "<<dirs>>/migrations"}<</ragtime?>>})

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
         :app  (handler-component (:app config))
         :http (jetty-server (:http config))<<#jdbc?>>
         :db   (hikaricp (:db config))<</jdbc?>><<#ragtime?>>
         :ragtime (ragtime (:ragtime config))<</ragtime?>><<#example?>>
         :example (endpoint-component example-endpoint)<</example?>><<#rest?>>
         :rest (endpoint-component rest-endpoint))<</rest?>>
        (component/system-using
         {:http [:app]
          :app  [<<#example?>>:example<</example?>><<#rest?>>:rest<</rest?>>]<<#ragtime?>>
          :ragtime [:db]<</ragtime?>><<#example?>>
          :example [<<#jdbc?>>:db<</jdbc?>>]<</example?>>}))))
