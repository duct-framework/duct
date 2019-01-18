(ns leiningen.new.external-profiles
  (:require [clojure.string]
            [leiningen.new]
            [leiningen.core.classpath :as cp]
            [leiningen.core.project :as project]
            [leiningen.core.user :as user]
            [leiningen.core.main :refer [abort]])
  (:import [java.io FileNotFoundException]))

(def ^:dynamic *use-snapshots?* false)
(def ^:dynamic *template-version* nil)

(defn- fake-project [name]
  (let [template-symbol (symbol name "duct-profile")
        template-version (cond *template-version* *template-version*
                               *use-snapshots?*   "(0.0.0,)"
                               :else              "RELEASE")
        user-profiles (:user (user/profiles))
        repositories (reduce
                       (:reduce (meta project/default-repositories))
                       project/default-repositories
                       (:plugin-repositories user-profiles))]
    (merge {:templates [[template-symbol template-version]]
            :repositories repositories}
           (select-keys user-profiles [:mirrors]))))

(defn resolve-remote-template [name sym]
  (try (cp/resolve-dependencies :templates (fake-project name) :add-classpath? true)
       (require sym)
       true
       (catch clojure.lang.Compiler$CompilerException e
         (abort (str "Could not load template, failed with: " (.getMessage e))))
       (catch Exception e nil)))

(defn resolve-template [profile]
  (let [name (name profile)
        sym (symbol (str name ".duct-profile"))]
    (if (try (require sym)
             true
             (catch FileNotFoundException _
               (resolve-remote-template name sym)))
      (resolve (symbol (str sym "/" name)))
      (abort "Could not find template" name "on the classpath."))))

(defn main [profiles project-data]
  (->> (map #((resolve-template %) project-data) profiles)
       (apply merge-with into)))