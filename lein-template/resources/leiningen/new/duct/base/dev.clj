(ns dev
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [duct.core :as duct]
            [integrant.core :as ig]
            [integrant.repl :refer [config system prep init halt go reset]]
            [taoensso.timbre :as log]))

(defn read-config []
  (duct/read-config
   (io/resource "{{dirs}}/config.edn")
   (io/resource "dev.edn")))

(integrant.repl/set-prep! (comp duct/prep read-config))
