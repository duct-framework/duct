(ns duct.core
  (:require [compojure.core :as compojure]
            [clojure.java.io :as io]
            [integrant.core :as ig]
            [meta-merge.core :refer [meta-merge]]))

(def ^:private hooks (atom {}))

(defn- run-hooks []
  (doseq [f (vals @hooks)] (f)))

(defonce ^:private init-shutdown-hook
  (delay (.addShutdownHook (Runtime/getRuntime) (Thread. #'run-hooks))))

(defn add-shutdown-hook [k f]
  (force init-shutdown-hook)
  (swap! hooks assoc k f))

(defn remove-shutdown-hook [k]
  (swap! hooks dissoc k))

(def ^:private readers
  {'resource io/resource})

(defn read-config
  ([source]
   (ig/read-string {:readers readers} (slurp source)))
  ([source & sources]
   (apply meta-merge (read-config source) (map read-config sources))))

(defn- apply-modules [config]
  (if (contains? config ::modules)
    (let [config' (ig/init config [::modules])]
      ((::modules config') config'))
    config))

(defn prep [config]
  (-> config
      (doto ig/load-namespaces)
      (apply-modules)
      (doto ig/load-namespaces)))

(defmethod ig/init-key ::modules [_ modules]
  (apply comp (reverse modules)))

(defmethod ig/init-key ::handler [_ {:keys [endpoints middleware]}]
  ((apply comp (reverse middleware))
   (apply compojure/routes endpoints)))
