(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.stuartsierra/component "0.2.2"]
                 [compojure "1.2.0"]
                 [duct "0.1.0-SNAPSHOT"]
                 [environ "1.0.0"]
                 [meta-merge "0.1.0"]
                 [ring "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [ring-jetty-component "0.2.1"]]
  :plugins [[lein-environ "1.0.0"]]
  :main ^:skip-aot {{namespace}}.main
  :profiles
  {:local/dev  {}
   :local/test {}
   :uberjar    {:aot :all}
   :test [:local/test]
   :dev  [{:source-paths ["dev"]
           :repl-options {:init-ns user}
           :dependencies [[reloaded.repl "0.1.0"]
                          [org.clojure/tools.namespace "0.2.4"]
                          [kerodon "0.4.0"]]
           :env {:port 3000}}
          :local/dev]})
