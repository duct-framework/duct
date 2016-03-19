(ns {{namespace}}
  (:require [compojure.core :refer :all]))

(defn {{name}}-endpoint [config]
  (routes
   (GET "/" [] "Hello World")))
