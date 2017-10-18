(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0-beta2"]
                 [duct/core "0.6.1"]
                 [duct/module.logging "0.3.1"]
                 [duct/module.web "0.6.2"]{{#ataraxy?}}
                 [duct/module.ataraxy "0.2.0"]{{/ataraxy?}}{{#cljs?}}
                 [duct/module.cljs "0.3.1"]{{/cljs?}}{{#jdbc?}}
                 [duct/module.sql "0.3.2"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "42.1.4"]{{/postgres?}}{{#sqlite?}}
                 [org.xerial/sqlite-jdbc "3.20.0"]{{/sqlite?}}]
  :plugins [[duct/lein-duct "0.10.3"]]
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name  "{{uberjar-name}}"{{/uberjar-name}}
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user{{#cljs?}}
                         :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]{{/cljs?}}}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.2.0"]
                                   [eftest "0.3.1"]
                                   [kerodon "0.8.0"]
                                   [com.gearswithingears/shrubbery "0.4.1"]]}})
