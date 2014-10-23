(ns {{namespace}}.endpoint.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]
            [duct.util.endpoint :refer [defendpoint]]))

(defendpoint example-endpoint []
  (GET "/" [] (io/resource "{{dirs}}/endpoint/example/welcome.html")))
