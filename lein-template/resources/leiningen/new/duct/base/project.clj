(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.stuartsierra/component "0.2.2"]
                 [compojure "1.3.1"]
                 [duct "0.0.8"]
                 [environ "1.0.0"]{{#heroku?}}{{#jdbc?}}
                 [hanami "0.1.0"]{{/jdbc?}}{{/heroku?}}
                 [meta-merge "0.1.1"]
                 [ring "1.3.2"]
                 [ring/ring-defaults "0.1.3"]
                 [ring-jetty-component "0.2.2"]{{#site?}}
                 [ring-webjars "0.1.0"]
                 [org.webjars/normalize.css "3.0.1"]{{/site?}}{{#jdbc?}}
                 [duct/hikaricp-component "0.1.0"]{{/jdbc?}}{{#postgres?}}
                 [org.postgresql/postgresql "9.3-1102-jdbc4"]{{/postgres?}}]
  :plugins [[lein-environ "1.0.0"]
            [lein-gen "0.2.2"]]
  :generators [[duct/generators "0.0.8"]]
  :duct {:ns-prefix {{namespace}}}
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name "{{uberjar-name}}"{{/uberjar-name}}
  :aliases {"gen"   ["generate"]
            "setup" ["do" ["generate" "locals"]]{{#heroku?}}
            "deploy" ["do"
                      ["vcs" "assert-committed"]
                      ["vcs" "push" "heroku" "master"]]{{/heroku?}}}
  :profiles
  {:dev  [:project/dev  :profiles/dev]
   :test [:project/test :profiles/test]
   :uberjar {:aot :all}
   :profiles/dev  {}
   :profiles/test {}
   :project/dev   {:source-paths ["dev"]
                   :repl-options {:init-ns user}
                   :dependencies [[reloaded.repl "0.1.0"]
                                  [org.clojure/tools.namespace "0.2.8"]
                                  [kerodon "0.5.0"]]
                   :env {:port 3000}}
   :project/test  {}})
