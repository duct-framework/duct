(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [duct/core "0.9.0-SNAPSHOT"]
                 [duct/module.logging "0.1.0-SNAPSHOT"]
                 [duct/module.web "0.1.0-SNAPSHOT"]]
  :plugins [[duct/lein-plugin "0.9.0-SNAPSHOT"]]
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name "{{uberjar-name}}"{{/uberjar-name}}
  :target-path "target/%s/"
  :prep-tasks ["javac" "compile" ["duct" "compile"]]
  :duct {:configs ["resources/{{dirs}}/config.edn"]}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]
   :uberjar {:aot :all}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:dependencies [[duct/repl "0.9.0-SNAPSHOT"]
                                  [kerodon "0.8.0"]]
                   :source-paths   ["dev/src"]
                   :resource-paths ["dev/resources"]
                   :repl-options   {:init-ns user}}
   :project/test  {}})
