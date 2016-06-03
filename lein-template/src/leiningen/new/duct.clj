(ns leiningen.new.duct
  (:require [clojure.java.io :as io]
            [leiningen.core.main :as main]
            [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path]]))

(def render (renderer "duct"))

(defn resource [name]
  (io/input-stream (io/resource (str "leiningen/new/duct/" name))))

(defmulti profile-data  (fn [module name] module))
(defmulti profile-files (fn [module data] module))

(defmethod profile-data  :default [_ _] {})
(defmethod profile-files :default [_ _] [])

(defmethod profile-data :base [_ name]
  (let [main-ns (sanitize-ns name)]
    {:raw-name    name
     :name        (project-name name)
     :namespace   main-ns
     :dirs        (name-to-path main-ns)
     :year        (year)
     :defaults    "api-defaults"}))

(defmethod profile-files :base [_ data]
  [["project.clj"               (render "base/project.clj" data)]
   ["README.md"                 (render "base/README.md" data)]
   [".gitignore"                (render "base/gitignore" data)]
   ["dev/user.clj"              (render "base/user.clj" data)]
   ["dev/dev.clj"               (render "base/dev.clj" data)]
   ["dev/dev/tasks.clj"         (render "base/tasks.clj" data)]
   ["src/{{dirs}}/main.clj"     (render "base/main.clj" data)]
   ["src/{{dirs}}/config.clj"   (render "base/config.clj" data)]
   ["src/{{dirs}}/system.clj"   (render "base/system.clj" data)]
   "src/{{dirs}}/boundary"
   "src/{{dirs}}/component"
   "src/{{dirs}}/endpoint"
   "test/{{dirs}}/boundary"
   "test/{{dirs}}/component"
   "test/{{dirs}}/endpoint"])

(defmethod profile-data :example [_ _]
  {:example? true})

(defmethod profile-files :example [_ data]
  (concat
   [["src/{{dirs}}/endpoint/example.clj"       (render "example/endpoint.clj" data)]
    ["test/{{dirs}}/endpoint/example_test.clj" (render "example/endpoint_test.clj" data)]]
   (if (:site? data)
     [["resources/{{dirs}}/endpoint/example/example.html"
       (resource "example/example.html")]])))

(defmethod profile-data :site [_ _]
  {:site?    true
   :static?  true
   :defaults "site-defaults"})

(defmethod profile-files :site [_ data]
  [["resources/{{dirs}}/public/favicon.ico"  (resource "site/favicon.ico")]
   ["resources/{{dirs}}/public/robots.txt"   (resource "site/robots.txt")]
   ["resources/{{dirs}}/public/css/site.css" (resource "site/site.css")]
   ["resources/{{dirs}}/errors/404.html"     (resource "site/404.html")]
   ["resources/{{dirs}}/errors/500.html"     (resource "site/500.html")]
   ["resources/{{dirs}}/public/index.html"   (render "site/index.html" data)]])

(defmethod profile-data :cljs [_ _]
  {:cljs? true
   :static? true})

(defmethod profile-files :cljs [_ data]
  [["dev/cljs/user.cljs" (render "cljs/user.cljs" data)]])

(defmethod profile-data :heroku [_ name]
  {:heroku? true
   :lein-deploy? true
   :uberjar-name (str (project-name name) "-standalone.jar")})

(defmethod profile-files :heroku [_ data]
  [["Procfile" (render "heroku/Procfile" data)]])

(defmethod profile-data :postgres [_ name]
  {:jdbc? true
   :postgres? true})

(defmethod profile-files :postgres [_ name] [])

(defmethod profile-data :sqlite [_ _]
  {:jdbc? true
   :sqlite? true})

(defmethod profile-files :sqlite [_ _] ["db"])

(defmethod profile-data :ragtime [_ _]
  {:jdbc? true
   :ragtime? true})

(defmethod profile-files :ragtime [_ _]
  ["resources/{{dirs}}/migrations"])

(defn profiles [hints]
  (for [hint hints :when (re-matches #"\+[A-Za-z0-9-]+" hint)]
    (keyword (subs hint 1))))

(defn duct
  "Create a new Duct web application.

Accepts the following profile hints:
  +cljs     - adds in ClojureScript compilation and hot-loading
  +example  - adds an example endpoint
  +heroku   - adds configuration for deploying to Heroku
  +postgres - adds a PostgreSQL dependency and database component
  +ragtime  - adds a Ragtime component to handle database migrations
  +site     - adds site middleware, a favicon, webjars and more
  +sqlite   - adds a SQLite dependency and database component"
  [name & hints]
  (when (.startsWith name "+")
    (main/abort "Failed to create project: no project name specified."))
  (main/info (str "Generating a new Duct project named " name "..."))
  (let [mods  (cons :base (profiles hints))
        data  (reduce into {} (map #(profile-data % name) mods))
        files (reduce into [] (map #(profile-files % data) mods))]
    (apply ->files data files))
  (main/info "Run 'lein setup' in the project directory to create local config files."))
