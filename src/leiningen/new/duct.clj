(ns leiningen.new.duct
  (:require [leiningen.core.main :as main]
            [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]))

(defn duct
  "FIXME: write documentation"
  [name]
  (let [render  (renderer "duct")
        main-ns (multi-segment (sanitize-ns name))
        data    {:raw-name    name
                 :name        (project-name name)
                 :namespace   main-ns
                 :nested-dirs (name-to-path main-ns)
                 :year        (year)}]
    (main/info "Generating a new Duct project.")
    (->files data
             ["project.clj"  (render "project.clj" data)]
             [".gitignore"   (render "gitignore" data)]
             ["dev/user.clj" (render "user.clj" data)]
             ["profiles.clj.sample"  (render "profiles.clj" data)]
             ["dev/local.clj.sample" (render "local.clj" data)]
             ["src/{{nested-dirs}}/handler.clj" (render "handler.clj" data)]
             ["src/{{nested-dirs}}/main.clj"    (render "main.clj" data)]
             ["src/{{nested-dirs}}/system.clj"  (render "system.clj" data)]
             "resources/public"
             "test/{{nested-dirs}}")))
