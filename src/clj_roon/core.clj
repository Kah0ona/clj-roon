(ns clj-roon.core
  (:require [clojurewerkz.machine-head.client :as mh]))


(def conn
  (mh/connect "tcp://roon:1883" (mh/generate-id)))

(def zone "Kantoor")

(def topic
  (str "roon/" zone "/now_playing/one_line/line1"))

(mh/subscribe conn
              {topic 0}
              (fn [^String topic _ ^bytes payload]
                (println (String. payload "UTF-8"))))

;; (mh/disconnect conn)

(defn send-cmd
  [zone command conn]
  (mh/publish conn (str "roon/" zone "/command") command))

(defn play
  [zone conn]
  (send-cmd  zone "play" conn))

(defn pause
  [zone conn]
  (send-cmd zone "pause" conn))

(defn next
  [zone conn]
  (send-cmd zone "next" conn))

(defn prev
  [zone conn]
  (send-cmd zone "previous" conn))

(defn stop
  [zone conn]
  (send-cmd zone "stop" conn))


(play zone conn)

(pause zone conn)

(next zone conn)

(prev  zone conn)

(stop zone conn)
