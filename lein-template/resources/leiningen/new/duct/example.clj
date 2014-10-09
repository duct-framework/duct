(ns {{namespace}}.component.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]
            [duct.component.routes :refer [routes-component]]))

(defn build-routes [config]
  (routes
   (GET "/" [] (io/resource "public/welcome.html"))))

(def component
  (routes-component build-routes))
