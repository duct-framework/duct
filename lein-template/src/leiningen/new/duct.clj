(ns leiningen.new.duct
  (:require [clojure.java.io :as io]
            [leiningen.core.classpath :as cp]
            [leiningen.core.main :as main]
            [leiningen.core.project :as project]
            [leiningen.core.user :as user]
            [leiningen.new.templates :as templates]))

(defn resource [path]
  (io/resource (str "leiningen/new/duct/" path)))

(defn project-data [raw-name profiles]
  (let [main-ns (templates/sanitize-ns raw-name)]
    {:raw-name     raw-name
     :project-ns   main-ns
     :project-name (templates/project-name raw-name)
     :project-path (templates/name-to-path main-ns)
     :profiles     (set profiles)}))

(defn base-profile [{:keys [project-name project-ns project-path raw-name]}]
  {:deps '[[duct/module.logging "0.4.0"]]
   :vars
   {:raw-name   raw-name
    :name       project-name
    :namespace  project-ns
    :dirs       project-path
    :year       (templates/year)}
   :dirs
   ["test/{{dirs}}"]
   :templates
   {"project.clj"                   (resource "base/project.clj")
    "README.md"                     (resource "base/README.md")
    ".gitignore"                    (resource "base/gitignore")
    "dev/src/user.clj"              (resource "base/user.clj")
    "dev/src/dev.clj"               (resource "base/dev.clj")
    "dev/resources/dev.edn"         (resource "base/dev.edn")
    "resources/{{dirs}}/config.edn" (resource "base/config.edn")
    "src/{{dirs}}/main.clj"         (resource "base/main.clj")
    "src/duct_hierarchy.edn"        (resource "base/duct_hierarchy.edn")}})

(defn profile-names [hints]
  (for [hint hints :when (re-matches #"\+[A-Za-z0-9-/.]+" hint)]
    (keyword (subs hint 1))))

(defn profile-function-symbol [profile-kw]
  (let [ns (or (namespace profile-kw) "duct")]
    (symbol (str ns ".duct-template")
            (str (name profile-kw) "-profile"))))

(defn profile-dependency [profile-kw]
  (let [ns (or (namespace profile-kw) "duct")]
    [(symbol ns "duct-template") "RELEASE"]))

(defn fake-lein-project [dep]
  (let [user-profiles (:user (user/profiles))
        repositories  (reduce
                       (:reduce (meta project/default-repositories))
                       project/default-repositories
                       (:plugin-repositories user-profiles))]
    (merge {:templates [dep] :repositories repositories}
           (select-keys user-profiles [:mirrors]))))

(defn try-require [sym]
  (try (require sym)
       true
       (catch clojure.lang.Compiler$CompilerException e
         (main/abort (str "Could not load template, failed with: " (.getMessage e))))
       (catch Exception _ nil)))

(defn try-require-remote [dep sym]
  (try (cp/resolve-dependencies :templates (fake-lein-project dep) :add-classpath? true)
       (try-require sym)
       (catch Exception _ nil)))

(defn profile-function [profile-kw]
  (let [sym (profile-function-symbol profile-kw)
        ns  (symbol (namespace sym))]
    (or (try-require ns)
        (try-require-remote (profile-dependency profile-kw) ns))
    (if-let [v (resolve sym)]
      (var-get v)
      (main/abort (str "Could not find var " sym " for profile " profile-kw)))))

(defn merge-deps [a b]
  (-> (sorted-map) (into a) (into b) vec))

(defn merge-profiles [a b]
  {:vars      (merge (:vars a) (:vars b))
   :dirs      (into (set (:dirs a)) (:dirs b))
   :deps      (merge-deps (:deps a) (:deps b))
   :dev-deps  (merge-deps (:dev-deps a) (:dev-deps b))
   :templates (into (:templates a) (:templates b))})

(defn project-template [name hints]
  (let [profiles (profile-names hints)
        data     (project-data name profiles)]
    (->> profiles
         (map profile-function)
         (map #(% data))
         (reduce merge-profiles (base-profile data)))))

(defn render-resource [data template]
  (templates/render-text (slurp template) data))

(defn render-templates [data templates]
  (->> templates
       (sort-by key)
       (map (fn [[path temp]] [path (render-resource data temp)]))))

(defn generate-project [{:keys [vars templates] :as profile}]
  (let [data  (merge vars (select-keys profile [:deps :dev-deps]))
        files (render-templates data templates)]
    (apply templates/->files data files)))

(defn duct
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
  (generate-project (project-template name hints))
  (main/info "Run 'lein duct setup' in the project directory to create local config files."))
