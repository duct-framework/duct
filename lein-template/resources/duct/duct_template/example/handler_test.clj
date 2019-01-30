(ns {{namespace}}.handler.example-test
  (:require [clojure.test :refer :all]
            [integrant.core :as ig]
            [ring.mock.request :as mock]
            [{{namespace}}.handler.example :as example]))

(deftest smoke-test
  (testing "example page exists"
    (let [handler  (ig/init-key :{{namespace}}.handler/example {})
          response (handler (mock/request :get "/example"))]
      (is {{#ataraxy?}}(= :ataraxy.response/ok (first response)){{/ataraxy?}}{{^ataraxy?}}(= 200 (:status response)){{/ataraxy?}} "response ok"))))
