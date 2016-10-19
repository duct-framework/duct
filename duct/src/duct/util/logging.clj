(ns duct.util.logging
  (:require [duct.util.config :as config]
            [medley.core :as m]
            [taoensso.timbre :as timbre]))

(defn- duct->timbre-config [config]
  (-> config
      (select-keys [:level :ns-whitelist :ns-blacklist :appenders])
      (update :appenders #(m/map-vals config/apply-fn %))))

(defn set-config! [{:keys [application/logging]}]
  (timbre/set-config! (duct->timbre-config logging)))
