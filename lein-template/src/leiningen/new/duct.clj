(ns leiningen.new.duct
  (:require [clojure.java.io :as io]
            [leiningen.core.main :as main]
            [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path]]))

(def render (renderer "duct"))

(defn resource [name]
  (io/input-stream (io/resource (str "leiningen/new/duct/" name))))

(defn duct
  "Create a new Duct project."
  [name]
  (let [main-ns (sanitize-ns name)
        data    {:raw-name    name
                 :name        (project-name name)
                 :namespace   main-ns
                 :dirs        (name-to-path main-ns)
                 :year        (year)}]
    (main/info (str "Generating a new Duct project named " name "..."))
    (main/warn "WARNING: This template is still experimental.")
    (->files data
      ["project.clj"  (render "project.clj" data)]
      ["README.md"    (render "README.md" data)]
      [".gitignore"   (render "gitignore" data)]
      ["dev/user.clj" (render "user.clj" data)]

      ["profiles.clj.sample"  (render "profiles.clj" data)]
      ["dev/local.clj.sample" (render "local.clj" data)]

      ["src/{{dirs}}/main.clj"   (render "main.clj" data)]
      ["src/{{dirs}}/system.clj" (render "system.clj" data)]

      "src/{{dirs}}/component"
      "src/{{dirs}}/endpoint"

      ["src/{{dirs}}/endpoint/example.clj"       (render "example.clj" data)]
      ["test/{{dirs}}/endpoint/example_test.clj" (render "example_test.clj" data)]
      ["resources/example/welcome.html"          (render "welcome.html" data)]

      ["resources/errors/404.html" (resource "404.html")]
      ["resources/errors/500.html" (resource "500.html")]

      ["resources/public/favicon.ico"       (resource "favicon.ico")]
      ["resources/public/css/normalize.css" (render "normalize.css" data)]
      ["resources/public/css/site.css"      (render "site.css" data)])))
