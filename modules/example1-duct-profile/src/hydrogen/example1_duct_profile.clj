(ns hydrogen.example1-duct-profile
  (:require [leiningen.new.templates :refer [renderer]]))

(def render (renderer "example1"))

(defn main [data]
  {:extra-deps ['[magnetcoop/stork "0.1.5"]]
   :extra-files [["resources/{{dirs}}/parrot.txt" (render "parrot.txt" data)]
                 ["src/{{dirs}}/take_over_the_world.clj" (render "take_over_the_world.clj" data)]]})
