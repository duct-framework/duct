(ns duct.middleware.route-aliases)

(defn wrap-route-aliases [handler aliases]
  (fn [request]
    (if-let [alias (aliases (:uri request))]
      (handler (assoc request :uri alias))
      (handler request))))
