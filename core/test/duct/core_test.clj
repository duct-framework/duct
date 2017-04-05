(ns duct.core-test
  (:require [clojure.test :refer :all]
            [duct.core :refer :all]))

(deftest test-add-shutdown-hook
  (let [f #(identity true)
        hooks (add-shutdown-hook ::foo f)]
    (is (= f (::foo hooks)))))

(deftest test-remove-shutdown-hook
  (add-shutdown-hook ::foo #(identity true))
  (let [hooks (remove-shutdown-hook ::foo)]
    (is (nil? (::foo hooks)))))
