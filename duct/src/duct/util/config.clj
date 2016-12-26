(ns duct.util.config
  (:refer-clojure :exclude [read])
  (:require [clojure.java.io :as io]
            [integrant.core :as ig]
            [meta-merge.core :refer [meta-merge]]))

(def ^:private readers
  {'resource io/resource})

(defn read
  ([source]
   (doto (ig/read-string {:readers readers} (slurp source))
     (ig/load-namespaces)))
  ([source & sources]
   (apply meta-merge (read source) (map read sources))))
