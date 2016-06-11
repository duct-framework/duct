(ns duct.util.namespace)

(defn load-var [sym]
  (require (symbol (namespace sym)))
  (find-var sym))
