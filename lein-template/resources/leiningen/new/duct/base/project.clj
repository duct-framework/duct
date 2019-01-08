(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [duct/core "0.7.0"]
                 [duct/module.logging "0.4.0"]{{#web?}}
                 [duct/module.web "0.7.0"]{{/web?}}{{#ataraxy?}}
                 [duct/module.ataraxy "0.3.0"]{{/ataraxy?}}{{#cljs?}}
                 [duct/module.cljs "0.4.0"]{{/cljs?}}{{#jdbc?}}
                 [duct/module.sql "0.5.0"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "42.2.5"]{{/postgres?}}{{#sqlite?}}
                 [org.xerial/sqlite-jdbc "3.25.2"]{{/sqlite?}}]
  :plugins [[duct/lein-duct "0.11.1"]]
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name  "{{uberjar-name}}"{{/uberjar-name}}
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]{{#cljs?}}
          :dependencies [[cider/piggieback "0.3.10"]]{{/cljs?}}
          :repl-options {:init-ns user{{#cljs?}}
                         :nrepl-middleware [cider.piggieback/wrap-cljs-repl]{{/cljs?}}}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.1"]
                                   [eftest "0.5.4"]{{#web?}}
                                   [kerodon "0.9.0"]{{/web?}}]}})
