(ns leiningen.new.duct
  (:require [clojure.java.io :as io]
            [leiningen.core.main :as main]
            [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path]]))

(def render (renderer "duct"))

(defn resource [name]
  (io/input-stream (io/resource (str "leiningen/new/duct/" name))))

(defmulti module-data  (fn [module name] module))
(defmulti module-files (fn [module data] module))

(defmethod module-data  :default [_ _] {})
(defmethod module-files :default [_ _] [])

(defmethod module-data :base [_ name]
  (let [main-ns (sanitize-ns name)]
    {:raw-name    name
     :name        (project-name name)
     :namespace   main-ns
     :dirs        (name-to-path main-ns)
     :year        (year)
     :defaults    "api-defaults"}))

(defmethod module-files :base [_ data]
  [["project.clj"               (render "base/project.clj" data)]
   ["README.md"                 (render "base/README.md" data)]
   [".gitignore"                (render "base/gitignore" data)]
   ["dev/user.clj"              (render "base/user.clj" data)]
   ["profiles.clj.sample"       (render "base/profiles.clj" data)]
   ["dev/local.clj.sample"      (render "base/local.clj" data)]
   ["src/{{dirs}}/main.clj"     (render "base/main.clj" data)]
   ["src/{{dirs}}/system.clj"   (render "base/system.clj" data)]
   ["resources/errors/404.html" (render "base/404.html" data)]
   ["resources/errors/500.html" (render "base/500.html" data)]
   "src/{{dirs}}/component"
   "src/{{dirs}}/endpoint"
   "test/{{dirs}}"])

(defmethod module-data :example [_ _]
  {:example? true})

(defmethod module-files :example [_ data]
  [["src/{{dirs}}/endpoint/example.clj"       (render "example/endpoint.clj" data)]
   ["test/{{dirs}}/endpoint/example_test.clj" (render "example/endpoint_test.clj" data)]
   "resources/{{dirs}}/endpoint/example"])

(defmethod module-data :site [_ _]
  {:site?    true
   :defaults "site-defaults"})

(defmethod module-files :site [_ data]
  (concat
   [["resources/public/favicon.ico"  (resource "site/favicon.ico")]
    ["resources/public/css/site.css" (render "site/site.css" data)]]
   (if (:example? data)
     [["resources/{{dirs}}/endpoint/example/welcome.html"
       (render "site/welcome.html" data)]])))

(defn active-modules [args]
  (for [arg args :when (re-matches #"\+[A-Za-z0-9-]+" arg)]
    (keyword (subs arg 1))))

(defn duct
  "Create a new Duct project."
  [name & args]
  (main/info (str "Generating a new Duct project named " name "..."))
  (main/warn "WARNING: This template is still experimental.")
  (let [mods  (cons :base (active-modules args))
        data  (reduce into {} (map #(module-data % name) mods))
        files (reduce into [] (map #(module-files % data) mods))]
    (apply ->files data files)))
