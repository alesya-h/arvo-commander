(ns arvocmd.fs.nav-location
  (:require
   ["fs-plus" :as fs]
   [arvocmd.log :as log]
   [reagent.core :as r]))

(defn make-raw-location [path]
  (r/atom {:current-path path :show-hidden false :items []}))

(defn reload-items [loc]
  (let [cur-path (:current-path @loc)]
    (fs/readdir cur-path
                (fn [err items]
                  (if err
                    (println err)
                    (swap! loc
                           #(if (= cur-path (:current-path %))
                              (assoc % :items (js->clj items))
                              %)))))))

(defn make-location [path]
  (doto (make-raw-location path)
    reload-items))

(defn navigate-to [loc path]
  (swap! loc assoc :current-path path)
  (reload-items loc))

(defn hidden-file? [filename]
  (.startsWith filename "."))

(defn items [loc]
  (into [".."]
        (cond->> (:items @loc)
          (not (:show-hidden @loc)) (remove hidden-file?))))

(defn cwd [loc] (-> @loc :current-path fs/tildify))
(defn title [loc] (cwd loc))

#_(defprotocol NavLocation
  (items [_])
  (title [_]))
