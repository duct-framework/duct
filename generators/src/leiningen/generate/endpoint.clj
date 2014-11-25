(ns leiningen.generate.endpoint
  (:require [leiningen.generate.templates :as tmpl]
            [leiningen.new.templates :refer [name-to-path]]
            [leiningen.core.main :as main]))

(defn endpoint
  "Generate a new Duct endpoint"
  [project name]
  (let [ns-prefix (-> project :duct :ns-prefix)
        namespace (str ns-prefix ".endpoint." name)
        data      {:name      name
                   :namespace namespace
                   :path      (name-to-path namespace)}]
    (when-not ns-prefix
      (main/abort ":ns-prefix not set in :duct map."))
    (doto (tmpl/renderer "endpoint")
      (tmpl/create-file "source.clj" "src/{{path}}.clj" data)
      (tmpl/create-file "test.clj" "test/{{path}}_test.clj" data))))
