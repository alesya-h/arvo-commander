(ns arvocmd.components.bottom-buttons)

(defn bottom-buttons []
  [:listbar {:bg "blue"
             :width "100%"
             ;; :style {:item {:bg "blue"} :selected {:bg "red"}}
             ;; :autoCommandKeys false
             :items ["Help" "Menu" "View" "Edit" "Copy"
                     "RenMov" "MkDir" "Delete" "PullDn" "Quit"]
             }])
