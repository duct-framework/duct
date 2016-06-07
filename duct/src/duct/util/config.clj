(ns duct.util.config
  (:require [clojure.edn :as edn]))

(defn read-config [source]
  (edn/read-string (slurp source)))
