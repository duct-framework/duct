(ns duct.component.handler-test
  (:require [clojure.test :refer :all]
            [compojure.core :as compojure]
            [com.stuartsierra.component :as component]
            [duct.component.handler :refer [handler-component]]
            [ring.mock.request :as mock]))

(defn- make-handler [deps opts]
  (-> (handler-component opts)
      (into deps)
      (component/start)
      :handler))

(defn- get-body [handler path]
  (:body (handler (mock/request :get path))))

(deftest test-different-routes
  (let [handler (make-handler
                 {:foo {:routes (compojure/GET "/foo" [] "foo")}
                  :bar {:routes (compojure/GET "/bar" [] "bar")}}
                 {})]
    (is (= (get-body handler "/foo") "foo"))
    (is (= (get-body handler "/bar") "bar"))))

(deftest test-conflicting-routes
  (let [handler (make-handler
                 {:foo {:routes (compojure/GET "/" [] "foo")}
                  :bar {:routes (compojure/GET "/" [] "bar")}}
                 {})]
    (is (= (get-body handler "/") "bar"))))

(deftest test-endpoint-option
  (let [handler (make-handler
                 {:foo {:routes (compojure/GET "/" [] "foo")}
                  :bar {:routes (compojure/GET "/" [] "bar")}}
                 {:endpoints [:foo :bar]})]
    (is (= (get-body handler "/") "foo"))))
