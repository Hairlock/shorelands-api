(ns shorelands-api.db.property.queries
  (:require [datomic.api :as d :refer [pull]]
            [shorelands-api.config :refer [env]]
            [shorelands-api.db.core :refer [conn]]
            [shorelands-api.db.property.core :refer [dbproperty->property]]))

(def valid-sort-keys #{:sellprice :rentprice :propertytype :name})

(defn get-properties []
  (let [properties (d/q '[:find [(pull ?pid [*]) ...]
                          :where [?pid :property/name _]]
                        (d/db conn))]
    (->> properties
         (map dbproperty->property)
         (sort-by :id))))


(defn sort-properties [sort-key]
  (if (contains? valid-sort-keys sort-key)
    (sort-by sort-key (get-properties))
    (-> (get-properties))))
