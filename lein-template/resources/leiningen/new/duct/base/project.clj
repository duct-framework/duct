(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]{{#cljs?}}
                 [org.clojure/clojurescript "1.8.51"]{{/cljs?}}
                 [com.stuartsierra/component "0.3.1"]
                 [compojure "1.5.1"]
                 [duct "0.7.0"]
                 [environ "1.0.3"]{{#heroku?}}{{#jdbc?}}
                 [hanami "0.1.0"]{{/jdbc?}}{{/heroku?}}
                 [meta-merge "1.0.0"]
                 [ring "1.5.0"]
                 [ring/ring-defaults "0.2.1"]
                 [ring-jetty-component "0.3.1"]{{#site?}}
                 [ring-webjars "0.1.1"]
                 [org.slf4j/slf4j-nop "1.7.21"]
                 [org.webjars/normalize.css "3.0.2"]{{/site?}}{{#jdbc?}}
                 [duct/hikaricp-component "0.1.0"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "9.4.1208"]{{/postgres?}}{{#sqlite?}}
                 [org.xerial/sqlite-jdbc "3.8.11.2"]{{/sqlite?}}{{#ragtime?}}
                 [duct/ragtime-component "0.1.4"]{{/ragtime?}}]
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
  :aliases {"setup"  ["run" "-m" "duct.util.repl/setup"]{{#heroku?}}
            "deploy" ["do"
                      ["vcs" "assert-committed"]
                      ["vcs" "push" "heroku" "master"]]{{/heroku?}}}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]{{#cljs?}}
   :repl {:resource-paths ^:replace ["resources" "dev/resources" "target/figwheel"]
          :prep-tasks     ^:replace [["javac"] ["compile"]]}{{/cljs?}}
   :uberjar {:aot :all}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:dependencies [[duct/generate "0.7.0"]
                                  [reloaded.repl "0.2.2"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [eftest "0.1.1"]
                                  [com.gearswithingears/shrubbery "0.3.1"]
                                  [kerodon "0.7.0"]{{#cljs?}}
                                  [binaryage/devtools "0.6.1"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [duct/figwheel-component "0.3.2"]
                                  [figwheel "0.5.0-6"]{{/cljs?}}]
                   :source-paths   ["dev/src"]
                   :resource-paths ["dev/resources"]
                   :repl-options {:init-ns user{{#cljs?}}
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]{{/cljs?}}}
                   :env {:port "3000"}}
   :project/test  {}})
