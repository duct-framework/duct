(ns duct.core
  "Core functions required by a Duct application."
  (:require [clojure.java.io :as io]
            [duct.core.protocols :as p]
            [duct.core.env :as env]
            [integrant.core :as ig]
            [meta-merge.core :refer [meta-merge]]))

(def ^:private hooks (atom {}))

(defn- run-hooks []
  (doseq [f (vals @hooks)] (f)))

(defonce ^:private init-shutdown-hook
  (delay (.addShutdownHook (Runtime/getRuntime) (Thread. #'run-hooks))))

(defn add-shutdown-hook
  "Set a function to be executed when the current process shuts down. The key
  argument should be unique, and is used in remove-shutdown-hook."
  [key func]
  (force init-shutdown-hook)
  (swap! hooks assoc key func))

(defn remove-shutdown-hook
  "Remove a shutdown hook identified by the specified key."
  [key]
  (swap! hooks dissoc key))

(defn not-in?
  "Return true if the map, m, does not contain a nested value identified by a
  sequence of keys, ks."
  [m ks]
  (let [o (Object.)] (identical? (get-in m ks o) o)))

(defn assoc-in-default
  "Functionally the same as assoc-in, except that it will not overwrite any
  existing value."
  [m ks default]
  (cond-> m (not-in? m ks) (assoc-in ks default)))

(def ^:private readers
  {'resource io/resource
   'env      env/env})

(defn read-config
  "Read an edn configuration from one or more slurpable sources. Multiple
  sources are meta-merged together. Three additional data readers are
  supported:

    #ref      - an Integrant reference to another key
    #resource - a resource path string, see clojure.java.io/resource
    #env      - an environment variable, see duct.core.env/env"
  ([source]
   (some->> source slurp (ig/read-string {:readers readers})))
  ([source & sources]
   (apply meta-merge (read-config source) (map read-config sources))))

(defn- apply-modules [config]
  (if (contains? config ::modules)
    (let [modules (::modules (ig/init config [::modules]))]
      (modules config))
    config))

(defn prep
  "Prep a configuration, ready to be initiated. Key namespaces are loaded,
  and modules are applied."
  [config]
  (-> config
      (doto ig/load-namespaces)
      (apply-modules)
      (doto ig/load-namespaces)))

(defn exec
  "Prep then initiate a configuration, and block indefinitely. This function
  should be called from -main when a standalone application is required."
  [config]
  (let [system (-> config prep ig/init)]
    (add-shutdown-hook ::exec #(ig/halt! system))
    (.. Thread currentThread join)))

(defmethod ig/init-key ::modules [_ modules]
  (apply comp (reverse modules)))

(defmethod ig/init-key ::environment [_ env] env)

(defmethod ig/init-key ::project-ns [_ ns] ns)

(defn- log-form [logger level event data form]
  `(p/-log ~logger ~level ~(str *ns*) ~*file* ~(:line (meta form)) ~event ~data))

(defmacro log
  "Log an event and optional data structure at the supplied severity level
  using a logger that implements the duct.core.protocols/Logger protocol."
  ([logger level event]      (log-form logger level event nil &form))
  ([logger level event data] (log-form logger level event data &form)))
