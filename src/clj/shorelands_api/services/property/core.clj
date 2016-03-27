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

(defn format-error-response [e]
  (let [ex (or (ex-data (.getCause e)) (ex-data e))
        error (:error ex)]
    (case (:cause ex)
      :ValidationFailed (bad-request {:error error})
      (internal-server-error))))

(def property-service
  (context "/api" []

           :tags ["Property"]

           (GET "/properties" []
                :return [Property]
                :query-params [{sort :- s/Str nil}
                               {page :- s/Int nil}
                               {limit :- s/Int nil}]
                :summary "returns a list of all available properties"
                :description "Returns an array of properties"
                :middleware [wrap-cors]
                (try
                  (ok (q/get-properties {:sort sort
                                         :limit limit
                                         :page page}))
                  (catch Exception e
                    (format-error-response e))))

           (POST "/properties" []
                 :body [property Property]
                 :return [Property]
                 :summary "updates the property given a property id"
                 :description "Accepts a property, nil values will fail validation"
                 :middleware [wrap-cors]
                 (try
                   (ok (t/update-property property))
                   (catch Exception e
                     (format-error-response e))))

           (PUT "/properties" []
                :body [property Property]
                :return [Property]
                :summary "attempts adds the given property to the database"
                :description "Property name needs to be a unique value"
                :middleware [wrap-cors]
                (try
                  (ok (t/create-property property))
                  (catch Exception e
                    (format-error-response e))))

           (DELETE "/properties" []
                   :body [property Property]
                   :return [Property]
                   :summary "attempts to delete the given property by name"
                   :description "Properties are not deleted permanently, see PATCH"
                   :middleware [wrap-cors]
                   (ok (t/delete-property property)))
           ))