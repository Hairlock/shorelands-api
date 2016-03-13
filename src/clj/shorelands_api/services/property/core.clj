(ns shorelands-api.services.property.core
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [shorelands-api.middleware :refer [wrap-cors]]
            [shorelands-api.db.property.queries :as q]))


(s/defschema Property
  {:id          Long
   :name        String
   :description String
   :sellprice   Float
   :rentprice   Float})

(def property-service
  (context "/api" []

           :tags ["Property"]

           (GET "/properties" []
                :return [Property]
                :summary "returns a list of all available properties"
                :description "Returns an array of properties"
                :middleware [wrap-cors]
                (ok (q/get-properties)))
           ))