{{=<< >>=}}
(ns <<namespace>>.endpoint.example-test
  (:require [clojure.test :refer :all]
            [kerodon.core :refer :all]
            [kerodon.test :refer :all]<<#rest?>>
            [ring.mock.request :as mock]<</rest?>>
            [<<namespace>>.endpoint.example :as example]))

(def handler
  (example/example-endpoint {}))

(deftest smoke-test<<#rest?>>
  (testing "rest endpoint returns json"
    (let [response (handler (mock/request :get "/hello"))]
      (is (= 200 (:status response)))
      (is (= {:hello "world"} (:body response)))))<</rest?>>
  (testing "index page exists"
    (-> (session handler)
        (visit "/")
        (has (status? 200) "page exists"))))
