(ns leiningen.generate.component
  (:require [clojure.string :as str]
            [leiningen.generate.templates :as tmpl]
            [leiningen.new.templates :refer [name-to-path]]
            [leiningen.core.main :as main]))

(defn camel-case [hyphenated]
  (->> (str/split hyphenated #"-")
       (map str/capitalize)
       (str/join)))

(defn component
  "Generate a new Duct component"
  [project name]
  (let [ns-prefix (-> project :duct :ns-prefix)
        namespace (str ns-prefix ".component." name)
        data      {:name      name
                   :namespace namespace
                   :path      (name-to-path namespace)
                   :record    (camel-case name)}]
    (when-not ns-prefix
      (main/abort ":ns-prefix not set in :duct map."))
    (doto (tmpl/renderer "component")
      (tmpl/create-file "source.clj" "src/{{path}}.clj" data)
      (tmpl/create-file "test.clj" "test/{{path}}_test.clj" data))))
