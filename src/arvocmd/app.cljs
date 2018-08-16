(ns arvocmd.app
  (:require
   [cljs.nodejs :as nodejs]
   ["path" :as path]
   ["react" :as react]
   ["blessed" :as blessed]
   ["react-blessed" :as react-blessed]
   [reagent.core :as r]
   [historian.core :as hist]
   [arvocmd.state :as state]))

(defonce screen nil)

(defn panel [name]
  [:list (merge (case name
                         :left {:left 0 :width "50%"}
                         :right {:left "50%" :right 0})
                       {:top 0
                        :height "100%"
                        :items ["1" "2" "3"]
                        :keys true
                        :mouse true
                        ;; :cwd "/home/me"
                        :focused (= name :left)
                        :interactive true
                        :border {:type "line"}
                        :bg "cyan"
                        :style {:border {:fg "red"}
                                :item {:fg "white"}
                                :selected {:bg "red"}}
                        })])

(defn header []
  [:box {:bg "blue"
         :width "100%"
         :top 0
         :left 0}])

(defn panels []
  [:box {:top 1
         :left 0
         :width "100%"
         :bottom 2}
   [panel :left]
   [panel :right]])


(defn status-line []
  [:box {:bg "blue" :width "100%" :height 1 :bottom 0 :left 0}])

(defn command-line []
  [:box {:bg "yellow" :width "100%" :height 1 :bottom 1 :left 0}])

(defn buttons []
  [:box {:bg "white" :width "100%" :height 1 :bottom 0 :left 0}])

(defn root-component []
  [:element
   [header]
   [panels]
   [status-line]
   [command-line]
   [buttons]])

(def r-root-component (r/reactify-component root-component))

(defn render-app []
  (react-blessed/render (react/createElement r-root-component #js {} nil)
                        screen))

(defn set-keys! []
  (doto screen
    (.key #js ["escape", "q", "C-c"] (fn [ch key] (js/process.exit 0)))
    (.key #js ["r"] #(render-app))))


(defonce initialization
  (do
    (nodejs/enable-util-print!)
    (state/update-state! merge {:top 0 :left 0 :width 50 :message "Hello world"})
    (set! screen (blessed/screen #js {:autoPadding true,
                                      :smartCSR true
                                      :title "react-blessed hello world"}))
    (js/setTimeout render-app 500)
    (set-keys!)
    ))

(defn init []
  (render-app))
