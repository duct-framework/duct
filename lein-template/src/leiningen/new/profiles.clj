(ns leiningen.new.profiles
  (:require [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path]]
            [leiningen.new.external-profiles :as external-profiles]))

(def render (renderer "duct"))

(defmulti profile-data  (fn [module name] module))
(defmulti profile-files (fn [module data] module))

(defmethod profile-data :default [module name]
  (external-profiles/profile-data module name))

(defmethod profile-files :default [module data]
  (external-profiles/profile-files module data))

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
   ["src/duct_hierarchy.edn"        (render "base/duct_hierarchy.edn" data)]
   "test/{{dirs}}"])

(def ^:private web-directories
  ["resources/{{dirs}}/public"
   "src/{{dirs}}/handler"
   "test/{{dirs}}/handler"])

(defmethod profile-data :example [_ _]
  {:example? true})

(defmethod profile-files :example [_ data]
  (if (:web? data)
    (concat
      [["src/{{dirs}}/handler/example.clj"       (render "example/handler.clj" data)]
       ["test/{{dirs}}/handler/example_test.clj" (render "example/handler_test.clj" data)]]
      (if (:site? data)
        [["resources/{{dirs}}/handler/example/example.html"
          (render "example/example.html" data)]]))
    [["src/{{dirs}}/service/example.clj"       (render "example/service.clj" data)]
     ["test/{{dirs}}/service/example_test.clj" (render "example/service_test.clj" data)]]))

(defmethod profile-data :api [_ _]
  {:api? true
   :web? true
   :deps [['duct/module.web "0.7.0"]]})

(defmethod profile-files :api [_ _]
  web-directories)

(defmethod profile-data :site [_ _]
  {:site? true
   :web? true
   :deps [['duct/module.web "0.7.0"]]})

(defmethod profile-files :site [_ data]
  web-directories)

(defmethod profile-data :cljs [_ _]
  {:cljs? true
   :site? true
   :web? true
   :deps [['duct/module.web "0.7.0"]
          ['duct/module.cljs "0.4.0"]]})

(defmethod profile-files :cljs [_ data]
  (conj web-directories
        ["src/{{dirs}}/client.cljs" (render "cljs/client.cljs" data)]))

(defmethod profile-data :heroku [_ name]
  {:heroku? true
   :uberjar-name (str (project-name name) "-standalone.jar")})

(defmethod profile-files :heroku [_ data]
  [["Procfile" (render "heroku/Procfile" data)]])

(defmethod profile-data :postgres [_ name]
  {:jdbc? true
   :postgres? true
   :dev-database "jdbc:postgresql://localhost/postgres"
   :deps [['duct/module.sql "0.5.0"]
          ['org.postgresql/postgresql "42.2.5"]]})

(defmethod profile-files :postgres [_ name] [])

(defmethod profile-data :sqlite [_ _]
  {:jdbc? true
   :sqlite? true
   :dev-database "jdbc:sqlite:db/dev.sqlite"
   :deps [['duct/module.sql "0.5.0"]
          ['org.xerial/sqlite-jdbc "3.25.2"]]})

(defmethod profile-files :sqlite [_ _] ["db"])

(defmethod profile-data :ataraxy [_ _]
  {:ataraxy? true
   :web? true
   :deps [['duct/module.web "0.7.0"]
          ['duct/module.ataraxy "0.3.0"]]})

(defmethod profile-files :ataraxy [_ _]
  web-directories)

(defn profiles [hints]
  (for [hint hints :when (re-matches #"^\++\w+(\.\w+)*$" hint)]
    (keyword (subs hint 1))))

(defn merge-profiles-data
  [& profiles]
  (if (every? coll? profiles)
    (vec (apply concat profiles))
    (some identity profiles)))
