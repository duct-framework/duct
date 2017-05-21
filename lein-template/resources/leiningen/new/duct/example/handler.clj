{{=<< >>=}}
(ns <<namespace>>.handler.example
  (:require <<^ataraxy?>>[compojure.core :refer :all]<</ataraxy?>><<#ataraxy?>>[ataraxy.core :as araraxy]
            [ataraxy.response :as response] <</ataraxy?>><<#site?>>
            [clojure.java.io :as io]<</site?>>
            [integrant.core :as ig]))

(defmethod ig/init-key :<<namespace>>.handler/example [_ options]<<^ataraxy?>>
  (context "/example" []
    (GET "/" []
      <<^site?>>"This is an example handler"<</site?>><<#site?>>(io/resource "<<dirs>>/handler/example/example.html")<</site?>>))<</ataraxy?>><<#ataraxy?>>
  (fn [{[_] :ataraxy/result}]
    <<^site?>>[::response/ok "This is an example handler"]<</site?>><<#site?>>[::response/ok (io/resource "<<dirs>>/handler/example/example.html")]<</site?>>)<</ataraxy?>>)
