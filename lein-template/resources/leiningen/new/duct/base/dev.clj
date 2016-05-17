{{=<< >>=}}
(ns dev
  (:refer-clojure :exclude [test])
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.generate :as gen]
            [meta-merge.core :refer [meta-merge]]
            [reloaded.repl :refer [system init start stop go reset]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace]]<<#cljs?>>
            [duct.component.figwheel :as figwheel]<</cljs?>>
            [dev.tasks :refer :all]
            [<<namespace>>.config :as config]
            [<<namespace>>.system :as system]))

(def dev-config
  {:app {:middleware [wrap-stacktrace]}<<#cljs?>>
   :figwheel
   {:css-dirs ["resources/<<dirs>>/public/css"]
    :builds   [{:source-paths ["src" "dev"]
                :build-options
                {:optimizations :none
                 :main "cljs.user"
                 :asset-path "/js"
                 :output-to  "target/figwheel/<<dirs>>/public/js/main.js"
                 :output-dir "target/figwheel/<<dirs>>/public/js"
                 :source-map true
                 :source-map-path "/js"}}]}<</cljs?>>})

(def config
  (meta-merge config/defaults
              config/environ
              dev-config))

(defn new-system []
  (into (system/new-system config)
        {<<#cljs?>>:figwheel (figwheel/server (:figwheel config))<</cljs?>>}))

<<#cljs?>>
(defn cljs-repl []
  (figwheel/cljs-repl (:figwheel system)))

<</cljs?>>
(when (io/resource "local.clj")
  (load "local"))

(gen/set-ns-prefix '<<namespace>>)

(reloaded.repl/set-init! new-system)
