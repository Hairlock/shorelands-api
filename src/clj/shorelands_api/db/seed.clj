(ns shorelands-api.db.seed
  (:require [datomic.api :as d]
            [shorelands-api.config :refer [env]]))


(def seed-data
  (load-file "resources/seed_data/properties.edn"))

(defn seed-db []
  (let [conn (d/connect (:database-uri env))]
    (d/transact
      conn
      seed-data)))