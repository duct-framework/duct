(ns leiningen.new.duct
  (:require [leiningen.core.main :as main]
            [leiningen.new.templates :refer [->files]]
            [leiningen.new.profiles :as profiles]
            [rewrite-clj.zip :as z]
            [clojure.java.io :as io]))

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
  (let [mods (cons :base (profiles/profiles hints))
        data (apply
               (partial merge-with profiles/merge-profiles-data)
               (map #(profiles/profile-data % name) mods))
        files (reduce into [] (map #(profiles/profile-files % data) mods))]
    (apply ->files data files))
  (main/info "Run 'lein duct setup' in the project directory to create local config files."))
