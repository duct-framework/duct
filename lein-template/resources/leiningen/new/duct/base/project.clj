(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [duct/core "0.2.2"]
                 [duct/module.logging "0.2.0"]
                 [duct/module.web "0.2.1"]{{#cljs?}}
                 [duct/module.cljs "0.2.0"]{{/cljs?}}{{#jdbc?}}
                 [duct/module.sql "0.2.0"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "9.4.1212"]{{/postgres?}}{{#sqlite?}}
                 [org.xerial/sqlite-jdbc "3.16.1"]{{/sqlite?}}]
  :plugins [[duct/lein-duct "0.9.0-alpha3"]]
  :main ^:skip-aot {{namespace}}.main
  :duct {:config-paths ["resources/{{dirs}}/config.edn"]}{{#uberjar-name}}
  :uberjar-name  "{{uberjar-name}}"{{/uberjar-name}}
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["duct" "compile"]]
  :profiles
  {:dev     [:project/dev :profiles/dev]
   :repl    {:prep-tasks   ^:replace ["javac" "compile"]
             :repl-options {:init-ns user{{#cljs?}}
                            :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]{{/cljs?}}}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.2.0"]
                                   [eftest "0.3.0"]
                                   [kerodon "0.8.0"]]}})
