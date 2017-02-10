(ns duct.core.protocols)

(defprotocol Logger
  "Protocol for abstracting logging."
  (log
    [logger level event]
    [logger level event data]
    "Log a namespaced keyword denoting an event with an optional map of
    additional data.")
  (log-ex [logger level exception]
    "Log an exception."))

(extend-protocol Logger
  nil
  (log [_ _ _])
  (log [_ _ _ _])
  (log-ex [_ _ _]))
