{{=<< >>=}}
(ns user
  (:require [clojure.repl :refer :all]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [meta-merge.core :refer [meta-merge]]
            [reloaded.repl :refer [system init start stop go reset]]
            [ring.middleware.stacktrace :refer [wrap-stacktrace]]<<#ragtime?>>
            [duct.component.ragtime :as ragtime]<</ragtime?>><<#cljs?>>
            [duct.component.figwheel :as figwheel]<</cljs?>>
            [<<namespace>>.config :as config]
            [<<namespace>>.system :as system]))

(def dev-config
  {:app {:middleware [wrap-stacktrace]}<<#cljs?>>
   :figwheel
   {:css-dirs ["resources/<<dirs>>/public/css"]
    :builds   [{:source-paths ["src" "dev"]
                :build-options
                {:optimizations :none
                 :output-to  "target/cljsbuild/out/<<dirs>>/public/js/main.js"
                 :output-dir "target/cljsbuild/out/<<dirs>>/public/js"
                 :source-map true
                 :source-map-path "js"}}]}<</cljs?>>})

(def config
  (meta-merge config/defaults
              config/environ
              dev-config))

(defn new-system []
  (into (system/new-system config)
        {<<#cljs?>>:figwheel (figwheel/server (:figwheel config))<</cljs?>>}))

<<#ragtime?>>
(defn migrate []
  (-> system :ragtime ragtime/reload ragtime/migrate))

(defn rollback
  ([]  (rollback 1))
  ([x] (-> system :ragtime ragtime/reload (ragtime/rollback x))))
<</ragtime?>>

(when (io/resource "local.clj")
  (load "local"))

(reloaded.repl/set-init! new-system)
