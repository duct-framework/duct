(ns duct.generate
  (:require [stencil.core :as stencil]
            [clj-time.core :as t]
            [clj-time.format :as tf]
            [clojure.java.io :as io]
            [clojure.string :as str]))

(def ^:dynamic *ns-prefix*
  "The common prefix all namespaces in the project share."
  nil)

(defn set-ns-prefix
  "Set the namespace prefix."
  [prefix]
  (alter-var-root #'*ns-prefix* (constantly (str prefix))))

(defn- name-to-classname [name]
  (str/replace name "-" "_"))

(defn- name-to-path [name]
  (-> name
      (name-to-classname)
      (str/replace "." java.io.File/separator)))

(defn camel-case [hyphenated]
  (->> (str/split hyphenated #"-")
       (map str/capitalize)
       (str/join)))

(defn- make-parent-dirs [path]
  (when-let [parent (.getParentFile (io/file path))]
    (.mkdirs parent)))

(defn- create-raw-file [data raw-string out-path]
  (let [out-path (stencil/render-string out-path data)]
    (println "Creating file" out-path)
    (make-parent-dirs out-path)
    (spit out-path raw-string)))

(defn- create-file [data in-path out-path]
  (create-raw-file data (stencil/render-file in-path data) out-path))

(defn- create-dir [data path]
  (let [path (stencil/render-string path data)]
    (println "Creating directory" path)
    (.mkdirs (io/file path))))

(defn locals
  "Generate local files: profiles.clj, dev/local.clj and .dir-locals.el."
  []
  (doto {}
    (create-file "duct/generate/templates/locals/local.clj" "dev/src/local.clj")
    (create-file "duct/generate/templates/locals/local.edn" "dev/resources/local.edn")
    (create-file "duct/generate/templates/locals/profiles.clj" "profiles.clj")
    (create-file "duct/generate/templates/locals/dir-locals.el" ".dir-locals.el"))
  nil)

(defn- assert-ns-prefix []
  (assert *ns-prefix* "duct.generate/*ns-prefix* not set"))

(defn endpoint
  "Generate a new Duct endpoint with the supplied name."
  [name]
  (assert-ns-prefix)
  (let [namespace (str *ns-prefix* ".endpoint." name)
        path      (name-to-path namespace)]
    (doto {:name (str name) :namespace namespace, :path path}
      (create-file "duct/generate/templates/endpoint/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/endpoint/test.clj" "test/{{path}}_test.clj")
      (create-dir "resources/{{path}}"))
    nil))

(defn component
  "Generate a new Duct component with the supplied name."
  [name]
  (assert-ns-prefix)
  (let [namespace (str *ns-prefix* ".component." name)
        path      (name-to-path namespace)
        record    (camel-case (str name))]
    (doto {:name (str name) :namespace namespace, :path path, :record record}
      (create-file "duct/generate/templates/component/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/component/test.clj" "test/{{path}}_test.clj"))
    nil))

(defn- class-name [x]
  (if (class? x)
    (.getName ^Class x)
    (str x)))

(defn- split-component [x]
    (rest (re-matches #"(.+)[./]([^./]+)" (class-name x))))

(defn boundary
  "Generate a new Duct boundary with the supplied name and component."
  [name component-sym]
  (assert-ns-prefix)
  (let [namespace           (str *ns-prefix* ".boundary." name)
        [comp-ns comp-class] (split-component component-sym)
        comp-name (name-to-classname (str comp-ns "." comp-class))]
    (doto {:name         (str name)
           :namespace    namespace
           :path         (name-to-path namespace)
           :protocol     (camel-case (str name))
           :component-ns comp-ns
           :component    comp-name}
      (create-file "duct/generate/templates/boundary/source.clj" "src/{{path}}.clj")
      (create-file "duct/generate/templates/boundary/test.clj" "test/{{path}}_test.clj"))
    nil))

(def ^:private timestamp-formatter
  (tf/formatter "yyyyMMddHHmmss"))

(defn sql-migration
  "Generate a Ragtime SQL migration with the supplied name."
  [name]
  (let [path      (name-to-path *ns-prefix*)
        timestamp (tf/unparse timestamp-formatter (t/now))]
    (doto {:name name, :timestamp timestamp, :path path}
      (create-raw-file "" "resources/{{path}}/migrations/{{timestamp}}-{{name}}.up.sql")
      (create-raw-file "" "resources/{{path}}/migrations/{{timestamp}}-{{name}}.down.sql"))
    nil))
