{{=<< >>=}}
(ns <<namespace>>.endpoint.example
  (:require [compojure.core :refer :all]<<#site?>>
            [clojure.java.io :as io]<</site?>>
            [integrant.core :as ig]))

(defn example-endpoint [options]
  (context "/example" []
    (GET "/" []
      <<^site?>>"This is an example endpoint"<</site?>><<#site?>>(io/resource "<<dirs>>/endpoint/example/example.html")<</site?>>)))

(defmethod ig/init-key :<<namespace>>.endpoint/example [_ options]
  (example-endpoint options))
