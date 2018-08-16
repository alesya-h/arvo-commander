(ns arvocmd.state
  (:require [reagent.core :as r]
            [historian.core :as hist]))

(defonce state (r/atom {}))

(defn update-state! [f & args]
  (apply swap! state f args))

(defn update-key! [k f & args]
  (apply swap! state update k f args))

(defn update-in-state! [path f & args]
  (apply swap! state update-in path f args))

(defn set-in-state! [path value]
  (update-in-state! path assoc key value))

(defn get-key [& args]
  (get-in @state args))

(defn set-key! [key value]
  (update-state! assoc key value))

(hist/record! state :state)
