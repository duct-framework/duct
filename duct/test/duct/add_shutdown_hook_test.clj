(ns duct.add-shutdown-hook-test
  (:require [clojure.test :refer :all]
            [duct.utils :refer [add-shutdown-hook!]]))

(deftest test-add-shutdown-hook
  (let [hook (add-shutdown-hook! #(identity true))]
    (is (true? (.removeShutdownHook (Runtime/getRuntime) hook)))))

