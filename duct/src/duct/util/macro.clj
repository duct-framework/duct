(ns duct.util.macro
  (:require [compojure.core :as compojure]
            [clojure.tools.macro :as macro]))

(defmacro defendpoint [name & body]
  (let [[name [bindings & routes]] (macro/name-with-attributes name body)]
    `(defn ~name [{:keys ~bindings}]
       (compojure/routes ~@routes))))
