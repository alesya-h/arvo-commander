(ns arvocmd.components.top-menu
  (:require
   [arvocmd.state :as s]))

(defonce state-atom (s/substate-atom :top-menu))

(def initial-state
  {:selected-path [0]
   :menus [{:name "Left" :items []}
           {:name "File" :items []}
           {:name "Command" :items []}
           {:name "Options" :items []}
           {:name "Right" :items []}
           ]})

(defn get-menu-item [n] (-> @state-atom :menus (get n)))

(defn menu-item-selected? [n]
  (= n (-> @state-atom :selected-path first)))

(defn top-menu-item-offset [idx]
  (->> @state-atom
      :menus
      (take idx)
      (map :name)
      (map count)
      (map #(+ % 3)) ; 1 space inside on each side, plus 1 space between items
      (apply +)))

(def unselected-item-style {:bg "blue"  :fg "black"})
(def selected-item-style   {:bg "red"   :fg "black"})

(defn menu-item [n]
  [:box (merge {:shrink true :left (top-menu-item-offset n)}
               (if (menu-item-selected? n) selected-item-style unselected-item-style))
    (str " " (:name (get-menu-item n)) " ")])

(reset! state-atom initial-state)

(defn top-menu []
  (into [:listbar {:bg "blue"
                   :width "100%"
                   ;; :style {:item {:bg "blue"} :selected {:bg "red"}}
                   ;; :autoCommandKeys false
                   ;; :items ["a" "b" "c"]#_(mapv :name (state/get-key :menu))
                   }]
   (mapv menu-item (-> @state-atom :menus count range))))
