(ns duct.core.protocols)

(defprotocol Logger
  "Protocol for abstracting logging. Used by the duct.core/log macro."
  (-log [logger level ns-str file line event data]))

(extend-protocol Logger
  nil
  (-log [_ _ _ _ _ _ _]))
