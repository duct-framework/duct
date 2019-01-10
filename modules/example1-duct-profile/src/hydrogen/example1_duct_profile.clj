(ns hydrogen.example1-duct-profile
  (:require [clojure.java.io :as io]))

(defn main [{:keys [project-name]}]
  {:extra-deps '[[magnetcoop/stork "0.1.5"]]
   :extra-files {(str "src/" project-name "/schedules.clj")
                 (io/resource "parrot.gif")}})

