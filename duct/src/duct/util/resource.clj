(ns duct.util.resource
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defprotocol FindPath
  (find-path [x]))

(extend-protocol FindPath
  String
  (find-path [s]
    (str/replace s #"^/|/$" ""))

  clojure.lang.Namespace
  (find-path [ns]
    (find-path (ns-name ns)))

  clojure.lang.Symbol
  (find-path [s]
    (-> (name s)
        (str/replace #"\." "/")
        (str/replace #"-" "_"))))

(defn path [& parts]
  (str/join "/" (map find-path parts)))

(defn url [& parts]
  (io/resource (apply path parts)))
