(ns duct.core
  (:require [clojure.java.io :as io]
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

(defn not-in? [m ks]
  (let [o (Object.)] (identical? (get-in m ks o) o)))

(defn assoc-in-default [m ks default]
  (cond-> m (not-in? m ks) (assoc-in ks default)))

(def ^:private readers
  {'resource io/resource})

(defn read-config
  ([source]
   (ig/read-string {:readers readers} (slurp source)))
  ([source & sources]
   (apply meta-merge (read-config source) (map read-config sources))))

(defn- apply-modules [config]
  (if (contains? config ::modules)
    (let [modules (::modules (ig/init config [::modules]))]
      (modules config))
    config))

(defn prep [config]
  (-> config
      (doto ig/load-namespaces)
      (apply-modules)
      (doto ig/load-namespaces)))

(defmethod ig/init-key ::modules [_ modules]
  (apply comp (reverse modules)))
