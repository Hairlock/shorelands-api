(ns shorelands-api.routes.services
  (:require [shorelands-api.services.user.core :refer [user-service]]
            [shorelands-api.services.property.core :refer [property-service]]
            [compojure.api.sweet :refer [defapi]]
            [compojure.api.swagger :refer [swagger-ui swagger-docs]]))


(defapi service-apis
        {:swagger {:ui "/swagger-ui"
                   :spec "/swagger.json"
                   :data {:info {:version "1.0.0"
                                 :title "Shorelands API"
                                 :description "Shorelands services"}
                          :tags [{:name "Users" :description "CRUD user details"}]}}}
        (swagger-docs
          {:info {:title "Shorelands Apis"
                  :version "0.0.1"
                  :contact {:name "Yannick Sealy"
                            :email "yannseal1@gmail.com"
                            :url "http://www.shorelandsrealestate.com"}}})
        user-service
        property-service)