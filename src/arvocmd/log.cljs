(ns arvocmd.log
  (:require
   [clojure.string :as string]
   [reagent.core :as r]))

(defonce log-state (r/atom '()))

(defn log! [& msgs]
  (->> msgs
       (map #(if (string? %) % (pr-str %)))
       (string/join " ")
       (swap! log-state conj)))

(defn current-log []
  (->> @log-state (string/join "\n")))
