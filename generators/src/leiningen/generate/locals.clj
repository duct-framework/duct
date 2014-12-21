(ns leiningen.generate.locals
  (:require [leiningen.generate.templates :as tmpl]))

(defn- dependency-in? [artifact project]
  (some #{artifact} (map first (:dependencies project))))

(defn- postgres-url [project]
  (if (dependency-in? 'hanami/hanami project)
    "postgres://localhost/postgres"
    "jdbc:postgresql://localhost/postgres"))

(defn- dev-database-url [project]
  (condp dependency-in? project
    'org.postgresql/postgresql (postgres-url project)
    nil))

(defn locals
  "Generate profiles.clj and dev/local.clj"
  [project]
  (let [data {:database-url (dev-database-url project)}]
    (doto (tmpl/renderer "locals")
      (tmpl/create-file "local.clj" "dev/local.clj" data)
      (tmpl/create-file "profiles.clj" "profiles.clj" data))))
