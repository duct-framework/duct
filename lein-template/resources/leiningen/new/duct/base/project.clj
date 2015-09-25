(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]{{#cljs?}}
                 [org.clojure/clojurescript "1.7.122"]{{/cljs?}}
                 [com.stuartsierra/component "0.3.0"]
                 [compojure "1.4.0"]
                 [duct "0.4.0"]
                 [environ "1.0.1"]{{#heroku?}}{{#jdbc?}}
                 [hanami "0.1.0"]{{/jdbc?}}{{/heroku?}}
                 [meta-merge "0.1.1"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [ring-jetty-component "0.2.2"]{{#site?}}
                 [ring-webjars "0.1.1"]
                 [org.slf4j/slf4j-nop "1.7.12"]
                 [org.webjars/normalize.css "3.0.2"]{{/site?}}{{#jdbc?}}
                 [duct/hikaricp-component "0.1.0"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "9.4-1203-jdbc4"]{{/postgres?}}{{#ragtime?}}
                 [duct/ragtime-component "0.1.2"]{{/ragtime?}}]
  :plugins [[lein-environ "1.0.1"]
            [lein-gen "0.2.2"]{{#cljs?}}
            [lein-cljsbuild "1.1.0"]{{/cljs?}}]
  :generators [[duct/generators "0.4.0"]]
  :duct {:ns-prefix {{namespace}}}
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name "{{uberjar-name}}"{{/uberjar-name}}
  :target-path "target/%s/"{{#cljs?}}
  :resource-paths ["resources" "target/cljsbuild"]
  :cljsbuild
  {:builds
   {:main {:jar true
           :source-paths ["src"]
           :compiler {:output-to "target/cljsbuild/{{dirs}}/public/js/main.js"
                      :optimizations :advanced}}}}{{/cljs?}}
  :aliases {"gen"   ["generate"]
            "setup" ["do" ["generate" "locals"]]{{#heroku?}}
            "deploy" ["do"
                      ["vcs" "assert-committed"]
                      ["vcs" "push" "heroku" "master"]]{{/heroku?}}}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]
   :uberjar {:aot :all{{#cljs?}}, :prep-tasks [["cljsbuild" "once"] ["compile"]]{{/cljs?}}}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:source-paths ["dev"]{{#cljs?}}
                   :resource-paths ^:replace ["resources" "target/figwheel"]{{/cljs?}}
                   :repl-options {:init-ns user}
                   :dependencies [[reloaded.repl "0.2.0"]
                                  [org.clojure/tools.namespace "0.2.11"]
                                  [kerodon "0.7.0"]{{#cljs?}}
                                  [duct/figwheel-component "0.2.0"]
                                  [figwheel "0.4.0"]{{/cljs?}}]
                   :env {:port 3000}}
   :project/test  {}})
