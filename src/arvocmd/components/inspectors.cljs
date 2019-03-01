(ns arvocmd.components.inspectors
  (:require
   [cljs.pprint :as pprint]
   [arvocmd.state :as s]
   [arvocmd.log :as log]))

(defn log-window []
  [:text {:border {:type "line"}
          :scrollable true
          :always-scroll true
          :scrollbar true
          :style {:bg "blue"}
          :bg "blue"
          :draggable true
          :label " Log "
          :content (log/current-log)
          :right 2 :bottom 4 :width 50 :height "40%"}
   ])

(def state-window-width 70)

(defn state-str []
  (with-out-str
    (binding [pprint/*print-right-margin* state-window-width]
      (pprint/pprint (assoc-in (s/get-current-state) [:twin-panels :panels] ::hidden)))))

(defn state-window []
  [:text {:border {:type "line"}
          :scrollbar true
          :transparent true
          :draggable true
          :label " State "
          :content (state-str)
          :right 55 :bottom 4 :width state-window-width :height "70%"}
   ])
