(ns leiningen.generate.locals
  (:require [leiningen.generate.templates :as tmpl]))

(defn locals
  "Generate profiles.clj and dev/local.clj"
  [project]
  (doto (tmpl/renderer "locals")
    (tmpl/create-file "local.clj" "dev/local.clj" {})
    (tmpl/create-file "profiles.clj" "profiles.clj" {})))
