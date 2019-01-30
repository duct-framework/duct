{{=<< >>=}}
(ns <<namespace>>.service.example
  (:require [duct.logger :as log]
            [integrant.core :as ig]))

(defmethod ig/prep-key :<<namespace>>.service/example [_ options]
  (merge {:logger (ig/ref :duct/logger)} options))

(defmethod ig/init-key :<<namespace>>.service/example [_ {:keys [logger]}]
  (log/log logger :report ::example-initiated))
