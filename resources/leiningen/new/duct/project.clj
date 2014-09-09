(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot {{namespace}}.main
  :profiles {:uberjar {:aot :all}})
