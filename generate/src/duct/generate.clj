(ns duct.generate
  (:require [leiningen.core.project :as project]
            [stencil.core :as stencil]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def ^:dynamic *ns-prefix*
  "The common prefix all namespaces in the project share."
  nil)

(defn set-ns-prefix
  "Set the namespace prefix."
  [prefix]
  (alter-var-root #'*ns-prefix* (constantly (str prefix))))

(defn- name-to-path [name]
  (-> name
      (str/replace "-" "_")
      (str/replace "." java.io.File/separator)))

(defn camel-case [hyphenated]
  (->> (str/split hyphenated #"-")
       (map str/capitalize)
       (str/join)))

(defn- make-parent-dirs [path]
  (when-let [parent (.getParentFile (io/file path))]
    (.mkdirs parent)))

(defn- create-file [data in-path out-path]
  (let [out-path (stencil/render-string out-path data)]
    (println "Creating file" out-path)
    (make-parent-dirs out-path)
    (spit out-path (stencil/render-file in-path data))))

(defn- create-dir [data path]
  (let [path (stencil/render-string path data)]
    (println "Creating directory" path)
    (.mkdirs (io/file path))))

(defn- dependency-in? [artifact project]
  (some #{artifact} (map first (:dependencies project))))

(defn- postgres-url [project]
  (if (dependency-in? 'hanami/hanami project)
    "postgres://localhost/postgres"
    "jdbc:postgresql://localhost/postgres"))

(defn- dev-database-url [project]
  (condp dependency-in? project
    'org.postgresql/postgresql (postgres-url project)
    'org.xerial/sqlite-jdbc    "jdbc:sqlite:db/dev.sqlite"
    nil))

(defn locals
  "Generate profiles.clj and dev/local.clj"
  []
  (let [project (project/read-raw "project.clj")]
    (doto {:database-url (dev-database-url project)}
      (create-file "duct/generate/templates/locals/local.clj" "dev/local.clj")
      (create-file "duct/generate/templates/locals/profiles.clj" "profiles.clj")
      (create-file "duct/generate/templates/locals/dir-locals.el" ".dir-locals.el"))
    nil))

(defn endpoint
  "Generate a new Duct endpoint"
  [name]
  (assert *ns-prefix* "duct.generate/*ns-prefix* not set")
  (let [project   (project/read-raw "project.clj")
        namespace (str *ns-prefix* ".endpoint." name)
        path      (name-to-path namespace)]
    (doto {:name name, :namespace namespace, :path path}
      (create-file "duct/generate/templates/endpoint/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/endpoint/test.clj" "test/{{path}}_test.clj")
      (create-dir "resources/{{path}}"))
    nil))

(defn component
  "Generate a new Duct component"
  [name]
  (assert *ns-prefix* "duct.generate/*ns-prefix* not set")
  (let [project   (project/read-raw "project.clj")
        namespace (str *ns-prefix* ".component." name)
        path      (name-to-path namespace)
        record    (camel-case name)]
    (doto {:name name, :namespace namespace, :path path, :record record}
      (create-file "duct/generate/templates/component/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/component/test.clj" "test/{{path}}_test.clj"))
    nil))
