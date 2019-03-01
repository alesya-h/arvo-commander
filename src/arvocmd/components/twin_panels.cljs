(ns arvocmd.components.twin-panels
  (:require
   [arvocmd.fs.nav-location :as nav-location]
   [arvocmd.state :as s]))

(defonce state-atom (s/substate-atom :twin-panels))

(def initial-state {:selected-panel 0
                    :panels {}})

(reset! state-atom initial-state)

(defonce selected-panel-atom (s/subsubstate-atom state-atom :selected-panel))
(defonce panels-atom (s/subsubstate-atom state-atom :panels))

(defn add-panel! [k panel-definition]
  (swap! panels-atom assoc k panel-definition))

(add-panel! 0 {:title "left"
               :position-and-size {:left 0 :width "50%"}
               :nav-location (nav-location/make-location "/home/me")})

(add-panel! 1 {:title "right"
               :position-and-size {:left "50%" :right 0}
               :nav-location (nav-location/make-location "/home/me/p")})

(defn another-panel [panel] (case panel 0 1 1 0))
(defn toggle-panel! [] (swap! selected-panel-atom another-panel))
(defn focused? [panel-id] (= panel-id @selected-panel-atom))

(defn panel-component [panel-atom]
  [:list
   (merge (:position-and-size @panel-atom)
          {:top 0
           :height "100%"
           :label (str " " (nav-location/title (:nav-location @panel-atom)) " ")
           :items (nav-location/items (:nav-location @panel-atom))
           :keys true
           :mouse true
           :focused (focused? (:id @panel-atom))
           :border {:type "line"}
           :style {;:bg "black"
                   :border {:fg "blue"}
                   :item   {:fg "white"}
                   :selected (if (focused? (:id @panel-atom))
                               {:fg "black" :bg "green"}
                               {:fg "white" :bg "black"})}})])

  ;; (prn @panels-atom)
(defn panel-wrapper [id]
  (let [panel-atom (s/subsubstate-atom panels-atom id)]
    ;; (prn panel-atom)
    (swap! panel-atom assoc :id id)
    [panel-component panel-atom]))

(defn active-path []
  (-> (get @panels-atom @selected-panel-atom)
      :nav-location
      nav-location/cwd))

(defn twin-panels []
  (into [:box {:width "100%"}]
        (mapv panel-wrapper (keys @panels-atom))))
