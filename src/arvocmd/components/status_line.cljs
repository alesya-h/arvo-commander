(ns arvocmd.components.status-line
  (:require
   [arvocmd.log :as log]))

(defn status-line []
  [:box {:style {:bg "blue"} :width "100%" :height 1 :content "Status line" #_(log/current-log)}])
