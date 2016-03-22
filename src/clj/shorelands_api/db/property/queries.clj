(ns shorelands-api.db.property.queries
  (:require [datomic.api :as d :refer [pull]]
            [shorelands-api.config :refer [env]]
            [shorelands-api.db.core :refer [conn]]))

(def valid-sort-keys #{:sellprice :rentprice :propertytype :name})

(defn format-property
  [property]
  (let [{id           :db/id name :property/name description :property/description
         sellprice    :property/sellprice rentprice :property/rentprice
         amenities    :property/amenities location :property/location
         propertytype :property/propertytype} property]
    {:id           id
     :name         name
     :location     location
     :description  description
     :amenities    amenities
     :propertytype propertytype
     :sellprice    sellprice
     :rentprice    rentprice}))


(defn get-properties []
  (let [properties (d/q '[:find [(pull ?pid [*]) ...]
                          :where [?pid :property/name _]]
                        (d/db conn))]
    (map format-property properties)))

(defn sort-properties [sort-key]
  (if (contains? valid-sort-keys sort-key)
    (sort-by sort-key (get-properties))
    (get-properties)))
