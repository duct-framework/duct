{{=<< >>=}}
(ns <<namespace>>.handler.example
  (:require <<^ataraxy?>>[compojure.core :refer :all]<</ataraxy?>><<#ataraxy?>>[ataraxy.core :as araraxy]
            [ring.util.response :as resp]<</ataraxy?>><<#site?>>
            [clojure.java.io :as io]<</site?>>
            [integrant.core :as ig]))

(defmethod ig/init-key :<<namespace>>.handler/example [_ options]<<^ataraxy?>>
  (context "/example" []
    (GET "/" []
      <<^site?>>"This is an example handler"<</site?>><<#site?>>(io/resource "<<dirs>>/handler/example/example.html")<</site?>>))<</ataraxy?>><<#ataraxy?>>
  (fn [{[_] :ataraxy/result}]
    <<^site?>>(resp/response "This is an example handler")<</site?>><<#site?>>(resp/resource-response "<<dirs>>/handler/example/example.html")<</site?>>)<</ataraxy?>>)
