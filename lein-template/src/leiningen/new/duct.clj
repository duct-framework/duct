(ns leiningen.new.duct
  (:require [clojure.java.io :as io]
            [leiningen.core.main :as main]
            [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]))

(defn duct
  "Create a new Duct project."
  [name]
  (let [render  (renderer "duct")
        main-ns (multi-segment (sanitize-ns name))
        data    {:raw-name    name
                 :name        (project-name name)
                 :namespace   main-ns
                 :nested-dirs (name-to-path main-ns)
                 :year        (year)}]
    (main/info (str "Generating a new Duct project named " name "..."))
    (->files data
             ["project.clj"  (render "project.clj" data)]
             [".gitignore"   (render "gitignore" data)]
             ["dev/user.clj" (render "user.clj" data)]
             ["profiles.clj.sample"  (render "profiles.clj" data)]
             ["dev/local.clj.sample" (render "local.clj" data)]
             ["src/{{nested-dirs}}/handler.clj" (render "handler.clj" data)]
             ["src/{{nested-dirs}}/main.clj"    (render "main.clj" data)]
             ["src/{{nested-dirs}}/system.clj"  (render "system.clj" data)]
             ["resources/public/favicon.ico"  (render "favicon.ico")]
             ["resources/public/404.html"     (render "404.html")]
             ["resources/public/500.html"     (render "500.html")]
             ["resources/public/welcome.html" (render "welcome.html")]
             ["resources/public/css/normalize.css" (render "normalize.css")]
             ["resources/public/css/site.css"      (render "site.css")]
             ["test/{{nested-dirs}}/handler_test.clj" (render "handler_test.clj" data)])))
