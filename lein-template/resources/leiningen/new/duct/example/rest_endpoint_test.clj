(ns {{namespace}}.endpoint.rest-example-test
  (:require [com.stuartsierra.component :as component]
            [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [{{namespace}}.endpoint.rest-example :as example]))

(def handler
  (example/rest-endpoint {}))

(deftest smoke-test
  (testing "rest endpoint return json"
    (let [response (handler (mock/request :get "/"))]
      (is (= 200 (:status response)))
      (is (= {:hello "world"} (:body response))))))
