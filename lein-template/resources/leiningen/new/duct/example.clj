(ns {{namespace}}.endpoint.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]))

(defn example-endpoint [config]
  (routes
   (GET "/" []
     (io/resource "public/welcome.html"))))
