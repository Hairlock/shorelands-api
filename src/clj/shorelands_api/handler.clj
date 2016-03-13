(ns shorelands-api.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [shorelands-api.layout :refer [error-page]]
            [shorelands-api.routes.home :refer [home-routes]]
            [shorelands-api.routes.services :refer [service-apis]]
            [compojure.route :as route]
            [shorelands-api.middleware :as middleware]))


(def app-routes
  (routes
    #'service-apis
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found nil)))

(def app (middleware/wrap-base #'app-routes))
