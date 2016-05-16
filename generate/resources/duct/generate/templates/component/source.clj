(ns {{namespace}}
  (:require [com.stuartsierra.component :as component]))

(defrecord {{record}} []
  component/Lifecycle
  (start [this] this)
  (stop [this] this))

(defn {{name}} []
  (->{{record}}))
