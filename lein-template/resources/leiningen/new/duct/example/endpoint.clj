{{=<< >>=}}
(ns <<namespace>>.endpoint.example
  (:require [compojure.core :refer :all]<<#rest?>>
            [ring.util.response :refer [response]]<</rest?>>
            [clojure.java.io :as io]))

<<#site?>>
(def welcome-page
  (io/resource "<<dirs>>/endpoint/example/welcome.html"))

<</site?>>
(defn example-endpoint [<<#jdbc?>>{{db :spec} :db}<</jdbc?>><<^jdbc?>>config<</jdbc?>>]
  (routes<<#rest?>>
    (GET "/hello" [] (response {:hello "world"}))<</rest?>>
    (GET "/" [] <<^site?>>"Hello World"<</site?>><<#site?>>welcome-page<</site?>>)))
