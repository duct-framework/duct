(ns {{namespace}}.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :as df]))

(defn build-routes [config]
  (routes
   (GET "/" [] "Hello World")
   (route/not-found "Not found")))

(defn new-handler [config]
  (-> (build-routes config)
      (df/wrap-defaults df/site-defaults)))
