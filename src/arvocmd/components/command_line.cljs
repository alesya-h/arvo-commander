(ns arvocmd.components.command-line
  (:require
   [arvocmd.components.twin-panels :as tp]))

(defn command-line []
  [:box {:width "100%" :height 1 :bottom 1 :left 0} (str " " (tp/active-path) " > ") ])
