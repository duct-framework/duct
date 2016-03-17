(defproject duct "0.5.10"
  :description "Support library for the Duct template."
  :url "https://github.com/weavejester/duct"
  :scm {:dir ".."}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.macro "0.1.5"]
                 [com.stuartsierra/component "0.3.1"]
                 [compojure "1.5.0"]
                 [ring/ring-core "1.4.0"]]
  :profiles
  {:dev {:dependencies [[ring/ring-mock "0.3.0"]]}})
