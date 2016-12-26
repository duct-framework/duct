(ns duct.core
  (:require [compojure.core :as compojure]
            [integrant.core :as ig]))

(defmethod ig/init-key ::handler [_ {:keys [endpoints middleware]}]
  ((apply comp (reverse middleware))
   (apply compojure/routes endpoints)))
