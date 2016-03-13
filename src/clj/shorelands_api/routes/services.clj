(ns shorelands-api.routes.services
  (:require [shorelands-api.services.user.core :refer [user-service]]
            [compojure.api.sweet :refer [defapi]]
            [compojure.api.swagger :refer [swagger-ui swagger-docs]]))


(defapi service-apis
        (swagger-ui "/swagger-ui")
        (swagger-docs
          {:info {:title "Shorelands Apis"
                  :version "0.0.1"}
           :tags [{:name "User"   :description "crud endpoints"}]})
        user-service)