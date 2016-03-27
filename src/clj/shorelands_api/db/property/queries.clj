(ns shorelands-api.db.property.queries
  (:require [datomic.api :as d :refer [pull]]
            [shorelands-api.config :refer [env]]
            [shorelands-api.db.core :refer [conn]]
            [shorelands-api.db.property.core :refer [dbproperty->property]]))

(def valid-sort-keys #{:sellprice :rentprice :propertytype :name :id})

(defn get-properties [transforms]
  (let [{:keys [sort page]} transforms
        properties (d/q '[:find [(pull ?pid [*]) ...]
                          :where [?pid :property/name _]]
                        (d/db conn))
        sort-fn (keyword (or sort :id))]
    (->> properties
         (map dbproperty->property)
         (sort-by sort-fn))))

