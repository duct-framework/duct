(ns {{namespace}}.handler-test
  (:require [clojure.test :refer [deftest is are testing]]
            [kerodon.core :refer [session visit follow-redirect follow fill-in choose
                                  check uncheck attach-file press within]]
            [kerodon.test :refer [has regex? text? status? value? missing? attr?]]
            [{{namespace}}.handler :as handler]
            [{{namespace}}.system :as system]))

(def handler
  (handler/new-handler (:app system/base-config)))

(deftest smoke-test
  (testing "index page exists"
    (-> (session handler)
        (visit "/")
        (has (status? 200) "page exists"))))
