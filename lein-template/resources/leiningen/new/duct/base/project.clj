(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]{{#cljs?}}
                 [org.clojure/clojurescript "1.8.51"]{{/cljs?}}
                 [com.stuartsierra/component "0.3.1"]
                 [compojure "1.5.0"]
                 [duct "0.5.10"]
                 [environ "1.0.3"]{{#heroku?}}{{#jdbc?}}
                 [hanami "0.1.0"]{{/jdbc?}}{{/heroku?}}
                 [meta-merge "0.1.1"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.2.0"]
                 [ring-jetty-component "0.3.1"]{{#site?}}
                 [ring-webjars "0.1.1"]
                 [org.slf4j/slf4j-nop "1.7.21"]
                 [org.webjars/normalize.css "3.0.2"]{{/site?}}{{#jdbc?}}
                 [duct/hikaricp-component "0.1.0"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "9.4.1208"]{{/postgres?}}{{#sqlite?}}
                 [org.xerial/sqlite-jdbc "3.8.11.2"]{{/sqlite?}}{{#ragtime?}}
                 [duct/ragtime-component "0.1.3"]{{/ragtime?}}]
  :plugins [[lein-environ "1.0.3"]{{#cljs?}}
            [lein-cljsbuild "1.1.2"]{{/cljs?}}]
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name "{{uberjar-name}}"{{/uberjar-name}}
  :target-path "target/%s/"{{#cljs?}}
  :resource-paths ["resources" "target/cljsbuild"]
  :prep-tasks [["javac"] ["cljsbuild" "once"] ["compile"]]
  :cljsbuild
  {:builds
   {:main {:jar true
           :source-paths ["src"]
           :compiler {:output-to "target/cljsbuild/{{dirs}}/public/js/main.js"
                      :optimizations :advanced}}}}{{/cljs?}}
  :aliases {"run-task" ["with-profile" "+task" "run" "-m"]
            "setup"    ["run-task" "dev.tasks/setup"]{{#heroku?}}
            "deploy"   ["do"
                        ["vcs" "assert-committed"]
                        ["vcs" "push" "heroku" "master"]]{{/heroku?}}}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]{{#cljs?}}
   :task {:prep-tasks ^:replace [["javac"] ["compile"]]}
   :repl {:resource-paths ^:replace ["resources" "target/figwheel"]
          :prep-tasks     ^:replace [["javac"] ["compile"]]}{{/cljs?}}
   :uberjar {:aot :all}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:dependencies [[duct/generate "0.5.10"]
                                  [reloaded.repl "0.2.1"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [eftest "0.1.1"]
                                  [kerodon "0.7.0"]{{#cljs?}}
                                  [com.cemerick/piggieback "0.2.1"]
                                  [duct/figwheel-component "0.3.2"]
                                  [figwheel "0.5.0-6"]{{/cljs?}}]
                   :source-paths ["dev"]
                   :repl-options {:init-ns user{{#cljs?}}
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]{{/cljs?}}}
                   :env {:port "3000"}}
   :project/test  {}})
