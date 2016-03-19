(ns duct.generate
  (:require [leiningen.core.project :as project]
            [stencil.core :as stencil]
            [clojure.java.io :as io]))

(defn- make-parent-dirs [path]
  (when-let [parent (.getParentFile (io/file path))]
    (.mkdirs parent)))

(defn- render-file [in-path out-path data]
  (make-parent-dirs out-path)
  (spit out-path (stencil/render-file in-path data)))

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
  (let [project (project/read-raw "project.clj")
        data    {:database-url (dev-database-url project)}]
    (render-file "leiningen/generate/locals/local.clj" "dev/local.clj" data)
    (render-file "leiningen/generate/locals/profiles.clj" "profiles.clj" data)))
