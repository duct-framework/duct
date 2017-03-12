{:default
 [:base :plugin.lein-duct/base :system :user :provided :dev]

 :base
 {:min-lein-version "2.0.0"
  :resource-paths   ^:replace ["resources" "%s/resources"]
  :target-path      "target/%s"
  :prep-tasks       [["duct" "compile"]]
  :repl-options     {:init-ns user}}

 :dev
 {:source-paths   ["dev/src"]
  :resource-paths ["dev/resources"]}

 :repl
 ^:repl {:prep-tasks ^:replace ["javac" "compile"]}

 :uberjar
 {:aot :all}

 :cljs
 {:repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
