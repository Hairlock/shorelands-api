(ns shorelands-api.services.property.core
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [shorelands-api.middleware :refer [wrap-cors]]
            [shorelands-api.db.property.queries :as q]
            [shorelands-api.db.property.transactions :as t]))


(s/defschema Property
  {:id           Long
   :name         String
   :description  String
   :location     String
   :amenities    String
   :propertytype String
   :sellprice    Long
   :rentprice    Long})

(def property-service
  (context "/api" []

           :tags ["Property"]

           (GET "/properties" []
                :return [Property]
                :summary "returns a list of all available properties"
                :description "Returns an array of properties"
                :middleware [wrap-cors]
                (ok (q/get-properties)))

           (POST "/properties" []
                  :body [property Property]
                  :return [Property]
                  :summary "updates the property given a property id"
                  :description "Accepts a property, nil values will fail validation"
                  :middleware [wrap-cors]
                  (ok (t/update-property property)))

           (PUT "/properties" []
                 :body [property Property]
                 :return [Property]
                 :summary "attempts adds the given property to the database"
                 :description "Property name needs to be a unique value"
                 :middleware [wrap-cors]
                 (ok (t/create-property property)))
           ))