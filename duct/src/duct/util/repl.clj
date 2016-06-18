(ns duct.util.repl
  (:refer-clojure :exclude [test])
  (:require [reloaded.repl :refer [system]]))

(defn setup []
  (require '[duct.generate :as gen])
  (eval '(gen/locals)))

(defn test []
  (require '[eftest.runner :as eftest])
  (eval '(eftest/run-tests (eftest/find-tests "test") {:multithread? false})))

(defn cljs-repl []
  (require '[duct.component.figwheel :as figwheel])
  (eval '(figwheel/cljs-repl (:figwheel system))))

(defn migrate []
  (require '[duct.component.ragtime :as ragtime])
  (eval '(-> system :ragtime ragtime/reload ragtime/migrate)))

(defn rollback
  ([]
   (rollback 1))
  ([x]
   (require '[duct.component.ragtime :as ragtime])
   (eval '(-> system :ragtime ragtime/reload (ragtime/rollback x)))))
