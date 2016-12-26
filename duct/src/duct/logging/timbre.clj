(ns duct.logging.timbre
  (:require [integrant.core :as ig]
            [taoensso.timbre :as timbre]))

(defmethod ig/init-key ::println [_ options]
  (timbre/println-appender options))

(defmethod ig/init-key ::spit [_ options]
  (timbre/spit-appender options))
