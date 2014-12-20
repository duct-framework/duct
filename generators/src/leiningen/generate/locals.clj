(ns leiningen.generate.locals
  (:require [leiningen.generate.templates :as tmpl]))

(defn- dependency-in? [artifact project]
  (some #{artifact} (map first (:dependencies project))))

(defn- dev-database-url [project]
  (condp dependency-in? project
    'org.postgresql/postgresql "jdbc:postgresql://localhost/postgres"
    nil))

(defn locals
  "Generate profiles.clj and dev/local.clj"
  [project]
  (let [data {:database-url (dev-database-url project)}]
    (doto (tmpl/renderer "locals")
      (tmpl/create-file "local.clj" "dev/local.clj" data)
      (tmpl/create-file "profiles.clj" "profiles.clj" data))))
