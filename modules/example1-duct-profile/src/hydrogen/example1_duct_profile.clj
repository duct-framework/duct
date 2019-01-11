(ns hydrogen.example1-duct-profile
  (:require [leiningen.new.templates :refer [renderer]]))

(def render (renderer "example1"))

(defn main [{:keys [project-name] :as data}]
  {:extra-deps ['[magnetcoop/stork "0.1.5"]]
   :extra-files [["resources/parrot.txt" (render "parrot.txt" data)]]})
