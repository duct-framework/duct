{{=<< >>=}}
(ns <<namespace>>.endpoint.example
  (:require [compojure.core :refer :all]
            [clojure.java.io :as io]))

<<#site?>>
(def example-page
  (io/resource "<<dirs>>/endpoint/example/example.html"))

<</site?>>
(defn example-endpoint [<<#jdbc?>>{{db :spec} :db}<</jdbc?>><<^jdbc?>>config<</jdbc?>>]
  (routes
   (GET "/example" [] <<^site?>>"This is an example endpoint"<</site?>><<#site?>>example-page<</site?>>)))
