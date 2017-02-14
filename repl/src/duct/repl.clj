(ns duct.repl
  (:refer-clojure :exclude [test])
  (:require [eftest.runner :as eftest]))

(defn test []
  (eftest/run-tests (eftest/find-tests "test") {:multithread? false}))
