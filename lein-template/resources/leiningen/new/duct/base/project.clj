(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [duct/core "0.5.1"]
                 [duct/module.logging "0.2.0"]
                 [duct/module.web "0.5.5"]{{#ataraxy?}}
                 [duct/module.ataraxy "0.1.6"]{{/ataraxy?}}{{#cljs?}}
                 [duct/module.cljs "0.2.3"]{{/cljs?}}{{#jdbc?}}
                 [duct/module.sql "0.2.2"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "42.1.1"]{{/postgres?}}{{#sqlite?}}
                 [org.xerial/sqlite-jdbc "3.19.3"]{{/sqlite?}}]
  :plugins [[duct/lein-duct "0.9.2"]]
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
