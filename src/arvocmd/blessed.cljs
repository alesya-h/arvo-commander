(ns arvocmd.blessed
  (:require
   [reagent.core :as r]
   ["react" :as react]
   ["neo-blessed" :as blessed]
   ["react-blessed" :as react-blessed]))

(defonce screen
  (blessed/screen #js {:autoPadding true,
                       :smartCSR true
                       :title "arvo-commander"}))

(def neo-blessed-render (react-blessed/createBlessedRenderer blessed))

(defn render-app [root-component]
  (let [reactified-root-component (r/reactify-component root-component)
        root-react-element (react/createElement reactified-root-component #js {} nil)]
    (neo-blessed-render root-react-element screen)))
