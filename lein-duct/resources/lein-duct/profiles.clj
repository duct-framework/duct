{:default
 {:min-lein-version "2.0.0"
  :resource-paths   ["%s/resources"]
  :target-path      "target/%s"
  :prep-tasks       [["duct" "compile"]]
  :repl-options     {:init-ns user}}

 :dev
 {:source-paths   ["dev/src"]
  :resource-paths ["dev/resources"]}

 :repl
 ^:repl {:prep-tasks ^:replace ["javac" "compile"]}

 :test
 {}

 :uberjar
 {:aot :all}}
