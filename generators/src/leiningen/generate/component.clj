(ns leiningen.generate.component
  (:require [clojure.string :as str]
            [leiningen.generate.templates :refer [renderer ->files]]
            [leiningen.new.templates :refer [name-to-path render-text]]
            [leiningen.core.main :as main]))

(def render (renderer "component"))

(defn camel-case [hyphenated]
  (->> (str/split hyphenated #"-")
       (map str/capitalize)
       (str/join)))

(defn component
  "Generate a new Duct component"
  [project name]
  (let [ns-prefix (-> project :duct :ns-prefix)]
    (when-not ns-prefix
      (main/abort ":ns-prefix not set in :duct map."))
    
    (let [namespace (str ns-prefix ".component." name)
          data {:name      name
                :namespace namespace
                :path      (name-to-path namespace)
                :record    (camel-case name)}]
      (main/info (render-text "Creating src/{{path}}.clj" data))
      (->files data ["src/{{path}}.clj" (render "source.clj" data)])

      (main/info (render-text "Creating test/{{path}}_test.clj" data))
      (->files data ["test/{{path}}_test.clj" (render "test.clj" data)]))))
