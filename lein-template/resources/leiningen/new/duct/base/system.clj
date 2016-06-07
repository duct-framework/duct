{{=<< >>=}}
(ns <<namespace>>.system
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]<<#jdbc?>>
            [duct.component.hikaricp :refer [hikaricp]]<</jdbc?>><<#ragtime?>>
            [duct.component.ragtime :refer [ragtime]]<</ragtime?>>
            [duct.middleware.not-found :refer [wrap-not-found]]<<#site?>>
            [duct.middleware.route-aliases :refer [wrap-route-aliases]]<</site?>>
            [duct.util.system :as system]
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults <<defaults>>]]<<#site?>>
            [ring.middleware.webjars :refer [wrap-webjars]]<</site?>>))

(def base-config
  {:app {:middleware [[wrap-not-found :not-found]<<#site?>>
                      [wrap-webjars]<</site?>>
                      [wrap-defaults :defaults]<<#site?>>
                      [wrap-route-aliases :aliases]<</site?>>]
         :not-found  <<^site?>>"Resource Not Found"<</site?>><<#site?>>(io/resource "<<dirs>>/errors/404.html")<</site?>>
         :defaults   (meta-merge <<defaults>> {<<#static?>>:static {:resources "<<dirs>>/public"}<</static?>>})<<#site?>>
         :aliases    {"/" "/index.html"}<</site?>>}<<#ragtime?>>
   :ragtime {:resource-path "<<dirs>>/migrations"}<</ragtime?>>})

(let [constructor (system/system-factory (edn/read-string (slurp (io/resource "<<namespace>>/system.edn"))))]
  (defn new-system [config]
    (constructor (meta-merge base-config config))))
