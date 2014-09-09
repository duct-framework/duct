(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.stuartsierra/component "0.2.2"]
                 [compojure "1.1.9"]
                 [environ "1.0.0"]
                 [ring "1.3.1"]
                 [ring/ring-defaults "0.1.1"]
                 [ring-jetty-component "0.2.0"]]
  :plugins [[lein-environ "1.0.0"]]
  :main ^:skip-aot {{namespace}}.main
  :profiles
  {:uberjar {:aot :all}
   :dev     {:env {:port 3000}}})
