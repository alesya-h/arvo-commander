(ns arvocmd.init
  (:require
   [arvocmd.log :as log]
   [cljs.nodejs :as nodejs]))

(defonce original-console-log js/console.log)
(defn log! [enabled?]
  (set! js/console.log
        (if enabled?
          original-console-log
          (or log/log! (fn [& args] nil)))))

(nodejs/enable-util-print!)
;; (log! true)
(log! false)
