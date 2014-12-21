(ns {{namespace}}.config
  (:require [environ.core :refer [env]]{{#jdbc?}}{{#heroku?}}
            [hanami.core :refer [jdbc-uri]]{{/heroku?}}{{/jdbc?}}))

(def defaults
  ^:displace {:http {:port 3000}})

(def environ
  {:http {:port (some-> env :port Integer.)}{{#jdbc?}}
   :db   {:uri  {{^heroku?}}(env :database-url){{/heroku?}}{{#heroku?}}(some-> env :database-url jdbc-uri){{/heroku?}}}{{/jdbc?}}})
