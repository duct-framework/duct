(ns duct.util.logging
  (:require [duct.util.config :as config]
            [medley.core :as m]
            [taoensso.timbre :as timbre]))

(defn- duct->timbre-appender [{:keys [function arguments]}]
  (if (or (nil? arguments) (seq? arguments))
    (apply function arguments)
    (function arguments)))

(defn- duct->timbre-config [config]
  (-> config
      (select-keys [:level :ns-whitelist :ns-blacklist :appenders])
      (update :appenders #(m/map-vals duct->timbre-appender %))))

(defn set-config! [{:keys [logging]}]
  (timbre/set-config! (duct->timbre-config logging)))
