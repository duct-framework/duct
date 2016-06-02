(ns {{namespace}}-test
  (:require [clojure.test :refer :all]
            [shrubbery.core :as shrub]
            [{{namespace}} :as {{name}}]))

(def handler
  ({{name}}/{{name}}-endpoint {}))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
