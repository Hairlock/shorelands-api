(ns shorelands-api.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [shorelands-api.layout :refer [error-page]]
            [shorelands-api.routes.services :refer [service-apis]]
            [compojure.route :as route]
            [shorelands-api.middleware :as middleware]))


(def app-routes
  (routes
    #'service-apis
    (route/not-found nil)))

(def app (middleware/wrap-base #'app-routes))
