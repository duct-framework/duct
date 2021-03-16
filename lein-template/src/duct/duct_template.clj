(ns duct.duct-template
  (:require [clojure.java.io :as io]))

(defn resource [path]
  (io/resource (str "duct/duct_template/" path)))

(def ^:private web-directories
  ["resources/{{dirs}}/public"
   "src/{{dirs}}/handler"
   "test/{{dirs}}/handler"])

(defn example-profile [{:keys [profiles project-ns]}]
  {:vars
   {:example?         true
    :cascading-routes (format "\n  [#ig/ref [:%s.handler/example]]" project-ns)}
   :templates
   (cond
     (profiles :site)
     {"src/{{dirs}}/handler/example.clj"                (resource "example/handler.clj")
      "test/{{dirs}}/handler/example_test.clj"          (resource "example/handler_test.clj")
      "resources/{{dirs}}/handler/example/example.html" (resource "example/example.html")}

     (profiles :api)
     {"src/{{dirs}}/handler/example.clj"       (resource "example/handler.clj")
      "test/{{dirs}}/handler/example_test.clj" (resource "example/handler_test.clj")}

     :else
     {"src/{{dirs}}/service/example.clj"       (resource "example/service.clj")
      "test/{{dirs}}/service/example_test.clj" (resource "example/service_test.clj")})})

(defn api-profile [_]
  {:deps     '[[duct/module.web "0.7.2"]]
   :dev-deps '[[kerodon "0.9.1"]]
   :vars     {:web? true, :api? true}
   :modules  {:duct.module.web/api {}}
   :dirs     web-directories})

(defn site-profile [_]
  {:deps     '[[duct/module.web "0.7.2"]]
   :dev-deps '[[kerodon "0.9.1"]]
   :vars     {:web? true, :site? true}
   :modules  {:duct.module.web/site {}}
   :dirs     web-directories})

(defn cljs-profile [{:keys [project-ns]}]
  {:deps         '[[duct/module.web "0.7.2"]
                   [duct/module.cljs "0.4.1"]]
   :dev-deps     '[[kerodon "0.9.1"]]
   :vars         {:cljs? true, :web? true}
   :dirs         web-directories
   :modules      {:duct.module/cljs {:main (symbol (str project-ns ".client"))}}
   :templates    {"src/{{dirs}}/client.cljs" (resource "cljs/client.cljs")}
   :repl-options {:nrepl-middleware '[cider.piggieback/wrap-cljs-repl]}})

(defn heroku-profile [{:keys [project-name]}]
  {:vars      {:uberjar-name (str project-name "-standalone.jar")}
   :templates {"Procfile" (resource "heroku/Procfile")}})

(defn postgres-profile [_]
  (let [postgresql-uri "jdbc:postgresql://localhost/postgres"]
    {:deps        '[[duct/module.sql "0.6.1"]
                    [org.postgresql/postgresql "42.2.19"]]
     :modules     {:duct.module/sql {}}
     :profile-dev {:duct.database/sql {:connection-uri postgresql-uri}}
     :vars        {:jdbc?        true
                   :postgres?    true
                   :dev-database postgresql-uri}}))

(defn sqlite-profile [_]
  (let [sqlite-uri "jdbc:sqlite:db/dev.sqlite"]
    {:deps        '[[duct/module.sql "0.6.1"]
                    [org.xerial/sqlite-jdbc "3.34.0"]]
     :dirs        ["db"]
     :modules     {:duct.module/sql {}}
     :profile-dev {:duct.database/sql
                   {:connection-uri sqlite-uri}}
     :vars        {:jdbc?        true
                   :sqlite?      true
                   :dev-database sqlite-uri}}))

(defn ataraxy-profile [_]
  {:deps '[[duct/module.ataraxy "0.3.0"]]
   :vars {:ataraxy? true, :web? true}
   :dirs web-directories})
