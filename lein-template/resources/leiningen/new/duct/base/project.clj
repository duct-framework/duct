(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [duct/core "0.7.0"]{{#deps}}
                 {{&.}}{{/deps}}]
  :plugins [[duct/lein-duct "0.12.1"]]
  :main ^:skip-aot {{namespace}}.main{{#uberjar-name}}
  :uberjar-name  "{{uberjar-name}}"{{/uberjar-name}}
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]{{#cljs?}}
          :dependencies [[cider/piggieback "0.4.0"]]{{/cljs?}}{{#repl-options}}
          :repl-options {{&.}}{{/repl-options}}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.1"]
                                   [eftest "0.5.7"]{{#dev-deps}}
                                   {{&.}}{{/dev-deps}}]}})
