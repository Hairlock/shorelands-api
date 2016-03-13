(ns shorelands-api.db.property.queries
  (:require [datomic.api :as d :refer [pull]]
            [shorelands-api.config :refer [env]]
            [shorelands-api.db.core :refer [conn]]))


(def db
  (->
    (:database-uri env)
    (d/connect)
    (d/db)))

(defn get-properties []
  (let [properties (d/q '[:find [(pull ?pid [*]) ...]
                          :where [?pid :property/name _]]
                        conn)]
    ;(map format-properties properties)
    ))


