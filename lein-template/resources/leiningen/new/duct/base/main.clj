(ns {{namespace}}.main
    (:gen-class)
    (:require [clojure.java.io :as io]
              [duct.core :as duct]))

(defn -main [& args]
  (duct/exec (duct/read-config (io/resource "{{dirs}}/config.edn"))))
