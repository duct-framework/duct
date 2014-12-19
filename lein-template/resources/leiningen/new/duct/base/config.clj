(ns {{namespace}}.config
  (:require [clojure.java.io :as io]
            [duct.middleware.errors :refer [wrap-hide-errors]]
            [duct.middleware.not-found :refer [wrap-not-found]]
            [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults {{defaults}}]]{{#site?}}
            [ring.middleware.webjars :refer [wrap-webjars]]{{/site?}}))

(def base
  {:http {:port 3000}
   :app  {:middleware [[wrap-not-found :not-found]{{#site?}}
                       [wrap-webjars]{{/site?}}
                       [wrap-defaults :defaults]]
          :not-found  {{^site?}}"Resource Not Found"{{/site?}}{{#site?}}(io/resource "errors/404.html"){{/site?}}
          :defaults   {{defaults}}}})

(def environ
  {:http {:port (some-> env :port Integer.)}})

(def production
  {:app {:middleware     [[wrap-hide-errors :internal-error]]
         :internal-error {{^site?}}"Internal Server Error"{{/site?}}{{#site?}}(io/resource "errors/500.html"){{/site?}}}})
