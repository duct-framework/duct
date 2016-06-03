(ns duct.generate
  (:require [leiningen.core.project :as project]
            [stencil.core :as stencil]
            [clj-time.core :as t]
            [clj-time.format :as tf]
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

(defn- create-raw-file [data raw-string out-path]
  (let [out-path (stencil/render-string out-path data)]
    (println "Creating file" out-path)
    (make-parent-dirs out-path)
    (spit out-path raw-string)))

(defn- create-file [data in-path out-path]
  (create-raw-file data (stencil/render-file in-path data) out-path))

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
  "Generate local files: profiles.clj, dev/local.clj and .dir-locals.el."
  []
  (let [project (project/read-raw "project.clj")]
    (doto {:database-url (dev-database-url project)}
      (create-file "duct/generate/templates/locals/local.clj" "dev/dev/local.clj")
      (create-file "duct/generate/templates/locals/profiles.clj" "profiles.clj")
      (create-file "duct/generate/templates/locals/dir-locals.el" ".dir-locals.el"))
    nil))

(defn- assert-ns-prefix []
  (assert *ns-prefix* "duct.generate/*ns-prefix* not set"))

(defn endpoint
  "Generate a new Duct endpoint with the supplied name."
  [name]
  (assert-ns-prefix)
  (let [namespace (str *ns-prefix* ".endpoint." name)
        path      (name-to-path namespace)]
    (doto {:name (str name) :namespace namespace, :path path}
      (create-file "duct/generate/templates/endpoint/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/endpoint/test.clj" "test/{{path}}_test.clj")
      (create-dir "resources/{{path}}"))
    nil))

(defn component
  "Generate a new Duct component with the supplied name."
  [name]
  (assert-ns-prefix)
  (let [namespace (str *ns-prefix* ".component." name)
        path      (name-to-path namespace)
        record    (camel-case (str name))]
    (doto {:name (str name) :namespace namespace, :path path, :record record}
      (create-file "duct/generate/templates/component/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/component/test.clj" "test/{{path}}_test.clj"))
    nil))

(defn- class-name [x]
  (if (class? x)
    (.getName ^Class x)
    (str x)))

(defn- split-component [x]
    (rest (re-matches #"(.+)[./]([^./]+)" (class-name x))))

(defn boundary
  "Generate a new Duct boundary with the supplied name and component."
  [name component-sym]
  (assert-ns-prefix)
  (let [namespace           (str *ns-prefix* ".boundary." name)
        [comp-ns comp-name] (split-component component-sym)]
    (doto {:name         (str name)
           :namespace    namespace
           :path         (name-to-path namespace)
           :protocol     (camel-case (str name))
           :component-ns comp-ns
           :component    comp-name}
      (create-file "duct/generate/templates/boundary/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/boundary/test.clj" "test/{{path}}_test.clj"))
    nil))

(def ^:private timestamp-formatter
  (tf/formatter "yyyyMMddHHmmss"))

(defn sql-migration
  "Generate a Ragtime SQL migration with the supplied name."
  [name]
  (let [path      (name-to-path *ns-prefix*)
        timestamp (tf/unparse timestamp-formatter (t/now))]
    (doto {:name name, :timestamp timestamp, :path path}
      (create-raw-file "" "resources/{{path}}/migrations/{{timestamp}}-{{name}}.up.sql")
      (create-raw-file "" "resources/{{path}}/migrations/{{timestamp}}-{{name}}.down.sql"))
    nil))
