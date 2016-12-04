(ns duct.util.config-test
  (:require [clojure.test :as t]
            [duct.util.config :as sut])
  (:import [java.io BufferedReader StringReader]))

(defn to-reader [x]
  (BufferedReader. (StringReader. (prn-str x))))

(t/deftest test-replace-namespaced-keywords
  (t/are [x y] (= x y)
    {:a :a, :a/a :a}
    (sut/read (to-reader {:a :a/a, :a/a :a}))
    {:a :b/a, :a/a :b/a}
    (sut/read (to-reader {:a :a/a, :a/a :b/a}))
    {:a :a/a}
    (sut/read (to-reader {:a :a/a}))))

