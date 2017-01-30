(ns duct.core
  (:require [compojure.core :as compojure]
            [integrant.core :as ig]))

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

(defmethod ig/init-key ::handler [_ {:keys [endpoints middleware]}]
  ((apply comp (reverse middleware))
   (apply compojure/routes endpoints)))
