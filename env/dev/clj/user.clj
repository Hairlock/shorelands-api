(ns user
  (:require [mount.core :as mount]
            shorelands-api.core))

(defn start []
  (mount/start-without #'shorelands-api.core/repl-server))

(defn stop []
  (mount/stop-except #'shorelands-api.core/repl-server))

(defn restart []
  (stop)
  (start))


