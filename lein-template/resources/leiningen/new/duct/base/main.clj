(ns {{namespace}}.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [duct.core :as duct]
            [integrant.core :as ig]))

(duct/load-hierarchy)

(defn -main [& args]
  (let [keys     (or (duct/parse-keys args) [:duct/daemon])
        profiles [:duct.profile/prod]]
    (-> (io/resource "{{dirs}}/config.edn")
        (duct/read-config)
        (duct/exec-config profiles keys))))
