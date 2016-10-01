(ns duct.util.namespace)

(defn load-var [sym]
  (require (symbol (namespace sym)))
  (find-var sym))

(defn resolve-var [sym-or-var]
  (if (symbol? sym-or-var) (load-var sym-or-var) sym-or-var))
