(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [duct/core "0.9.0-SNAPSHOT"]
                 [duct/module.logging "0.1.0-SNAPSHOT"]
                 [duct/module.web "0.1.0-SNAPSHOT"]{{#cljs?}}
                 [duct/module.cljs "0.1.0-SNAPSHOT"]{{/cljs?}}]
  :plugins [[duct/lein-duct "0.9.0-SNAPSHOT"]]
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name "{{uberjar-name}}"{{/uberjar-name}}
  :duct {:config-paths ["resources/{{dirs}}/config.edn"]}
  :profiles
  {:default [:leiningen/default :plugin.lein-duct/default]
   :dev     [:plugin.lein-duct/dev  :project/dev  :profiles/dev]
   :test    [:plugin.lein-duct/test :project/test :profiles/test]
   :uberjar [:plugin.lein-duct/uberjar]
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:dependencies [[duct/repl "0.9.0-SNAPSHOT"]
                                  [kerodon "0.8.0"]]}
   :project/test  {}})
