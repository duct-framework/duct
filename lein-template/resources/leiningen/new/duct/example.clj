(ns {{namespace}}.endpoint.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]
            [duct.util.macro :refer [defendpoint]]
            [duct.util.resource :as resource]))

(def this-ns *ns*)

(defendpoint example-endpoint []
  (GET "/" [] (resource/url this-ns "welcome.html")))
