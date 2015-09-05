{{=<< >>=}}
(ns <<namespace>>.endpoint.rest-example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]
            [ring.util.response :refer [response]]))

(defn rest-endpoint [<<#jdbc?>>{{db :spec} :db}<</jdbc?>><<^jdbc?>>config<</jdbc?>>]
  (routes
   (GET "/" []
        (response {:hello "world"}))))
