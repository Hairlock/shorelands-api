(ns shorelands-api.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [shorelands-api.layout :refer [error-page]]
            [shorelands-api.routes.home :refer [home-routes]]
            [shorelands-api.routes.oldservices :refer [service-routes]]
            [shorelands-api.routes.services :refer [service-apis]]
            [compojure.route :as route]
            [shorelands-api.middleware :as middleware]))


(def app-routes
  (routes
    #'service-routes
    #'service-apis
    (wrap-routes #'home-routes middleware/wrap-csrf)
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))

(def app (middleware/wrap-base #'app-routes))
