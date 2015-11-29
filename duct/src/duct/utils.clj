(ns duct.utils)

(defn add-shutdown-hook! [f]
  (let [hook (Thread. f)]
    (.addShutdownHook (Runtime/getRuntime) hook)
    hook))
