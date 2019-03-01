(ns arvocmd.state
  (:require
   [reagent.core :as r]
   [lentes.core :as l]
   [historian.core :as hist]))

(defonce state (r/atom {}))

(defn substate-atom [k]
  (l/derive (l/key k) state))

(defn subsubstate-atom [derived-state k]
  (l/derive (l/key k) derived-state))

(defn get-current-state [] @state)

(hist/record! state :state)
(def undo! hist/undo!)
(def redo! hist/redo!)
