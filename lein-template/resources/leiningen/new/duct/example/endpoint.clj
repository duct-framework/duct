{{=<< >>=}}
(ns <<namespace>>.endpoint.example
  (:require [compojure.core :refer :all]<<#site?>>
            [clojure.java.io :as io]<</site?>>))

(defn example-endpoint [<<#jdbc?>>{{db :spec} :db}<</jdbc?>><<^jdbc?>>config<</jdbc?>>]
  (context "/example" []
    (GET "/" []
      <<^site?>>"This is an example endpoint"<</site?>><<#site?>>(io/resource "<<dirs>>/endpoint/example/example.html")<</site?>>)))
