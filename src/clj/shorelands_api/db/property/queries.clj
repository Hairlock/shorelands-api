(ns shorelands-api.db.property.queries
  (:require [datomic.api :as d :refer [pull]]
            [shorelands-api.config :refer [env]]
            [shorelands-api.db.core :refer [conn]]))


(defn format-property
  [property]
  (let [{id :db/id name :property/name description :property/description
         sellprice :property/sellprice rentprice :property/rentprice } property]
    {:id id
     :name name
     :description description
     :sellprice sellprice
     :rentprice rentprice}))

(defn get-properties []
  (let [properties (d/q '[:find [(pull ?pid [*]) ...]
                          :where [?pid :property/name _]]
                        (d/db conn))]
    (map format-property properties)))


