(ns {{namespace}}.main
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [{{namespace}}.system :refer [new-system]]))

(defn -main [& args]
  (component/start (new-system {})))
