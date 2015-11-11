{{=<< >>=}}
(ns <<namespace>>.system
  (:require <<#site?>>[clojure.java.io :as io]
            <</site?>>[com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]<<#jdbc?>>
            [duct.component.hikaricp :refer [hikaricp]]<</jdbc?>><<#ragtime?>>
            [duct.component.ragtime :refer [ragtime]]<</ragtime?>>
            [duct.middleware.not-found :refer [wrap-not-found]]<<#site?>>
            [duct.middleware.route-aliases :refer [wrap-route-aliases]]<</site?>>
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults <<defaults>>]]<<#site?>>
            [ring.middleware.webjars :refer [wrap-webjars]]<</site?>><<#example?>>
            [<<namespace>>.endpoint.example :refer [example-endpoint]]<</example?>>))

(def base-config
  {:app {:middleware [[wrap-not-found :not-found]<<#site?>>
                      [wrap-webjars]<</site?>>
                      [wrap-defaults :defaults]<<#site?>>
                      [wrap-route-aliases :aliases]<</site?>>]
         :not-found  <<^site?>>"Resource Not Found"<</site?>><<#site?>>(io/resource "<<dirs>>/errors/404.html")<</site?>>
         :defaults   (meta-merge <<defaults>> {<<#static?>>:static {:resources "<<dirs>>/public"}<</static?>>})<<#site?>>
         :aliases    {"/" "/index.html"}<</site?>>}<<#ragtime?>>
   :ragtime {:resource-path "<<dirs>>/migrations"}<</ragtime?>>})

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
         :app  (handler-component (:app config))
         :http (jetty-server (:http config))<<#jdbc?>>
         :db   (hikaricp (:db config))<</jdbc?>><<#ragtime?>>
         :ragtime (ragtime (:ragtime config))<</ragtime?>><<#example?>>
         :example (endpoint-component example-endpoint)<</example?>>)
        (component/system-using
         {:http [:app]
          :app  [<<#example?>>:example<</example?>>]<<#ragtime?>>
          :ragtime [:db]<</ragtime?>><<#example?>>
          :example [<<#jdbc?>>:db<</jdbc?>>]<</example?>>}))))
