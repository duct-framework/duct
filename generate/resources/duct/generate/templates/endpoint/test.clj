(ns {{namespace}}-test
  (:require [clojure.test :refer :all]
            [{{namespace}} :as {{name}}]))

(def handler
  ({{name}}/{{name}}-endpoint {}))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
