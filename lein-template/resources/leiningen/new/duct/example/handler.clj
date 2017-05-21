{{=<< >>=}}
(ns <<namespace>>.handler.example
  (:require [compojure.core :refer :all]<<#site?>>
            [clojure.java.io :as io]<</site?>>
            [integrant.core :as ig]))

(defmethod ig/init-key :<<namespace>>.handler/example [_ options]
  (context "/example" []
    (GET "/" []
      <<^site?>>"This is an example handler"<</site?>><<#site?>>(io/resource "<<dirs>>/handler/example/example.html")<</site?>>)))
