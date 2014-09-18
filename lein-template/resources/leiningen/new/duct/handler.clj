(ns {{namespace}}.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.middleware.defaults :as df]))

(defn build-routes [config]
  (routes
   (GET "/" [] "Hello World")
   (route/not-found (io/resource "public/404.html"))))

(defn new-handler [config]
  (-> (build-routes config)
      (df/wrap-defaults df/site-defaults)))
