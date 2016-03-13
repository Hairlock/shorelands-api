(ns shorelands-api.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [shorelands-api.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[shorelands-api started successfully using the development profile]=-"))
   :middleware wrap-dev})
