(ns hydrogen.example2.duct-profile
  (:require [leiningen.new.templates :refer [renderer]]))

(def render (renderer "hydrogen.example2"))

(defn hydrogen.example2 [data]
  {:extra-deps ['[magnetcoop/stork "0.1.5"]
		'[aramis "0.1.1"]]
   :extra-files [["resources/{{dirs}}/parrot.txt" (render "parrot.txt" data)]
                 ["src/{{dirs}}/take_over_the_world.clj" (render "take_over_the_world.clj" data)]]})
