(ns {{namespace}}.config
  (:require [clojure.java.io :as io]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [environ.core :refer [env]]))

(def defaults
  ^:displace {:http {:port 3000}})

(def environ
  {:http {:port (some-> env :port Integer.)}})

(def production
  {:app {:middleware     [[wrap-hide-errors :internal-error]]
         :internal-error {{^site?}}"Internal Server Error"{{/site?}}{{#site?}}(io/resource "errors/500.html"){{/site?}}}})
