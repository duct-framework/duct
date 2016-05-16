(ns duct.generate
  (:require [leiningen.core.project :as project]
            [stencil.core :as stencil]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- name-to-path [name]
  (-> name
      (str/replace "-" "_")
      (str/replace "." java.io.File/separator)))

(defn- make-parent-dirs [path]
  (when-let [parent (.getParentFile (io/file path))]
    (.mkdirs parent)))

(defn- create-file [data in-path out-path]
  (let [out-path (stencil/render-string out-path data)]
    (make-parent-dirs out-path)
    (spit out-path (stencil/render-file in-path data))))

(defn- create-dir [data path]
  (.mkdirs (io/file (stencil/render-string path data))))

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
      (create-file "leiningen/generate/locals/local.clj" "dev/local.clj")
      (create-file "leiningen/generate/locals/profiles.clj" "profiles.clj"))
    nil))

(defn endpoint
  "Generate a new Duct endpoint"
  [name]
  (let [project   (project/read-raw "project.clj")
        ns-prefix (-> project :duct :ns-prefix)
        namespace (str ns-prefix ".endpoint." name)
        path      (name-to-path namespace)]
    (assert ns-prefix ":ns-prefix not set in :duct map.")
    (doto {:name name, :namespace namespace, :path path}
      (create-file "leiningen/generate/endpoint/source.clj" "src/{{path}}.clj")
      (create-file "leiningen/generate/endpoint/test.clj" "test/{{path}}_test.clj")
      (create-dir "resources/{{path}}"))
    nil))
