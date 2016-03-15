(ns shorelands-api.db.property.transactions)
  (:require [datomic.api :as d]
            [shorelands-api.db.core :refer [conn]]))


(defn add-property [property]
  @(d/transact (d/db conn) ))
