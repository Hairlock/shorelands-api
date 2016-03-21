(ns shorelands-api.db.property.transactions
  (:require [datomic.api :as d]
            [shorelands-api.db.core :refer [conn]]
            [shorelands-api.db.property.core :refer [property->dbproperty]]
            [shorelands-api.db.property.queries :refer [get-properties]]))


(defn update-property [property]
  (let [prop (property->dbproperty property)]
    @(d/transact conn [prop])
    (get-properties)))






