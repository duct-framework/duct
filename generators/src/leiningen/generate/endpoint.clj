(ns leiningen.generate.endpoint
  (:require [leiningen.generate.templates :refer [renderer ->files]]
            [leiningen.new.templates :refer [name-to-path render-text]]
            [leiningen.core.main :as main]
            [rewrite-clj.zip :as z]
            [rewrite-clj.zip.indent :as zi]))

(def render (renderer "endpoint"))

(defn- add-require [zipper require-decl]
  (-> zipper
      (z/find-value z/next 'ns)
      (z/find-value z/next :require)
      z/rightmost
      (z/insert-right require-decl)
      z/append-newline
      z/right
      (zi/indent 11)))

(defn- add-endpoint-to-system [file {:keys [name namespace]}]
  (-> (z/of-file file)
      (add-require [(symbol namespace) :refer [(symbol (str name "-endpoint"))]])
      z/print-root
      println))

(defn endpoint
  "Generate a new Duct endpoint"
  [project name]
  (let [ns-prefix (-> project :duct :ns-prefix)]
    (when-not ns-prefix
      (main/abort ":ns-prefix not set in :duct map."))
    
    (let [namespace   (str ns-prefix ".endpoint." name)
          system-file (str "src/" ns-prefix "/system.clj")
          data {:name      name
                :namespace namespace
                :path      (name-to-path namespace)
                :prefix    (name-to-path ns-prefix)}]
      ;(main/info (render-text "Creating src/{{path}}.clj" data))
      ;(->files data ["src/{{path}}.clj" (render "source.clj" data)])

      ;(main/info (render-text "Creating test/{{path}}_test.clj" data))
      ;(->files data ["test/{{path}}_test.clj" (render "test.clj" data)])

      (main/info (render-text "Editing src/{{prefix}}/system.clj" data))
      (add-endpoint-to-system system-file data))))
