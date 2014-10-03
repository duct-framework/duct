(ns {{namespace}}.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.middleware.defaults :as defaults]
            [duct.middleware.errors :refer [html-resource]]))

(defn build-routes [config]
  (routes
   (GET "/" [] (html-resource "public/welcome.html"))
   (route/not-found (html-resource "public/404.html"))))

(defn new-handler [config]
  (-> (build-routes config)
      (defaults/wrap-defaults defaults/site-defaults)))
