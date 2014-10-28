(ns leiningen.generate.endpoint
  (:require [leiningen.generate.templates :refer [renderer ->files]]
            [leiningen.new.templates :refer [name-to-path render-text]]
            [leiningen.core.main :as main]))

(def render (renderer "endpoint"))

(defn endpoint
  "Generate a new Duct endpoint"
  [project name]
  (let [ns-prefix (-> project :duct :ns-prefix)]
    (when-not ns-prefix
      (main/abort ":ns-prefix not set in :duct map."))
    
    (let [namespace (str ns-prefix ".endpoint." name)
          data {:name      name
                :namespace namespace
                :path      (name-to-path namespace)}]
      (main/info (render-text "Creating src/{{path}}.clj" data))
      (->files data ["src/{{path}}.clj" (render "source.clj" data)])

      (main/info (render-text "Creating test/{{path}}_test.clj" data))
      (->files data ["test/{{path}}_test.clj" (render "test.clj" data)]))))
