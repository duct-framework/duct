(defproject duct/generators "0.4.0"
  :description "Duct generators for lein-generate"
  :url "https://github.com/weavejester/duct"
  :scm {:dir ".."}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :dependencies [[lein-gen "0.2.2"]]
  :profiles {:dev {:plugins [[lein-gen "0.2.2"]]
                   :duct {:ns-prefix example}}})
