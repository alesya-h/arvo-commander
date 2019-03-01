(ns arvocmd.app
  (:require
   [arvocmd.init]
   ["path" :as path]
   [arvocmd.components.inspectors :as inspectors]
   [arvocmd.blessed :as b]
   [arvocmd.components.top-menu :as top-menu]
   [arvocmd.components.twin-panels :as twin-panels]
   [arvocmd.components.status-line :as status-line]
   [arvocmd.components.command-line :as command-line]
   [arvocmd.components.bottom-buttons :as bottom-buttons]
   [arvocmd.keymap :as keymap]))

(defn lines-main-lines-layout [{:keys [top-lines middle bottom-lines]}]
  [:element
   (into [:layout {:width "100%" :top 0 :height (count top-lines)}] top-lines)
   [:layout {:width "100%" :top (count top-lines) :bottom (count bottom-lines)} middle]
   (into [:layout {:width "100%" :bottom 0 :height (count bottom-lines)}] bottom-lines)])

(defn root-component []
  [:element
   (lines-main-lines-layout
    {:top-lines [[top-menu/top-menu]]
     :middle [twin-panels/twin-panels]
     :bottom-lines [;[status-line/status-line]
                    [command-line/command-line]
                    [bottom-buttons/bottom-buttons]]})
   [inspectors/log-window]
   [inspectors/state-window]
   ])

(defn render-app []
  (b/render-app root-component))

(defonce initialization
  (do
    (keymap/register-keys! ["r"] #(render-app))
    (keymap/set-keys!)
    (js/setTimeout render-app 500)))

(defn init [] (render-app))
