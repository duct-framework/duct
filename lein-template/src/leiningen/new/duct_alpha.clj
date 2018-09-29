(ns leiningen.new.duct-alpha
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
     :web-module  :duct.module/web}))

(defmethod profile-files :base [_ data]
  [["project.clj"                   (render "base/project.clj" data)]
   ["README.md"                     (render "base/README.md" data)]
   [".gitignore"                    (render "base/gitignore" data)]
   ["dev/src/user.clj"              (render "base/user.clj" data)]
   ["dev/src/dev.clj"               (render "base/dev.clj" data)]
   ["dev/resources/dev.edn"         (render "base/dev.edn" data)]
   ["resources/{{dirs}}/config.edn" (render "base/config.edn" data)]
   ["src/{{dirs}}/main.clj"         (render "base/main.clj" data)]
   "resources/{{dirs}}/public"
   "src/{{dirs}}/boundary"
   "src/{{dirs}}/handler"
   "test/{{dirs}}/boundary"
   "test/{{dirs}}/handler"])

(defmethod profile-data :example [_ _]
  {:example? true})

(defmethod profile-files :example [_ data]
  (concat
   [["src/{{dirs}}/handler/example.clj"       (render "example/handler.clj" data)]
    ["test/{{dirs}}/handler/example_test.clj" (render "example/handler_test.clj" data)]]
   (if (:site? data)
     [["resources/{{dirs}}/handler/example/example.html"
       (render "example/example.html" data)]])))

(defmethod profile-data :api [_ _]
  {:api? true, :web? true})

(defmethod profile-files :api [_ _] [])

(defmethod profile-data :site [_ _]
  {:site? true, :web? true})

(defmethod profile-files :site [_ data] [])

(defmethod profile-data :cljs [_ _]
  {:cljs? true})

(defmethod profile-files :cljs [_ data]
  [["src/{{dirs}}/client.cljs" (render "cljs/client.cljs" data)]])

(defmethod profile-data :heroku [_ name]
  {:heroku? true
   :uberjar-name (str (project-name name) "-standalone.jar")})

(defmethod profile-files :heroku [_ data]
  [["Procfile" (render "heroku/Procfile" data)]])

(defmethod profile-data :postgres [_ name]
  {:jdbc? true
   :postgres? true
   :dev-database "jdbc:postgresql://localhost/postgres"})

(defmethod profile-files :postgres [_ name] [])

(defmethod profile-data :sqlite [_ _]
  {:jdbc? true
   :sqlite? true
   :dev-database "jdbc:sqlite:db/dev.sqlite"})

(defmethod profile-files :sqlite [_ _] ["db"])

(defmethod profile-data :ataraxy [_ _]
  {:ataraxy? true})

(defmethod profile-files :ataraxy [_ _] [])

(defn profiles [hints]
  (for [hint hints :when (re-matches #"\+[A-Za-z0-9-]+" hint)]
    (keyword (subs hint 1))))

(defn duct-alpha
  "Create a new Duct web application.

Accepts the following profile hints:
  +api      - adds API middleware and handlers
  +ataraxy  - adds the Ataraxy router
  +cljs     - adds in ClojureScript compilation and hot-loading
  +example  - adds an example handler
  +heroku   - adds configuration for deploying to Heroku
  +postgres - adds a PostgreSQL dependency and database component
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
  (main/info "Run 'lein duct setup' in the project directory to create local config files."))
