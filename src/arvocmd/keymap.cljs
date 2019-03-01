(ns arvocmd.keymap
  (:require
   [arvocmd.blessed :as b]
   [arvocmd.components.twin-panels :as twin-panels]
   [arvocmd.log :as log]
   [arvocmd.state :as s]))

(def keymap-atom (s/substate-atom :keys))

(def base-keys
  {["escape", "q", "C-c"] (fn [ch key] (js/process.exit 0))
   ["l"] #(log/log! "monkeys!")
   ["S-l"] #(log/log! "MONKEYS!")
   ["u"] s/undo!
   ["C-r"] s/redo!
   ["tab"] twin-panels/toggle-panel!})

(reset! keymap-atom base-keys)

(defn register-keys! [ks f]
  (swap! keymap-atom assoc ks f))

(defn set-keys! []
  (doseq [[k f] @keymap-atom]
    (.key b/screen (clj->js k) f)))
