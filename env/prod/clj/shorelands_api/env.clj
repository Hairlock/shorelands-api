(ns shorelands-api.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[shorelands-api started successfully]=-"))
   :middleware identity})
