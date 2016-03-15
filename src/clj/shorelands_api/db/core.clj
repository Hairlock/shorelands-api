(ns shorelands-api.db.core
  (:require
    [datomic.api :as d]
    [clojure.tools.logging :refer [info]]
    [shorelands-api.config :refer [env]]
    [shorelands-api.db.schema :refer [schemas]]
    [shorelands-api.db.seed :refer [seed-db]]
    [mount.core :refer [defstate]]))




(defn- new-connection [conf]
  (let [uri (:database-uri conf)]
    (info "Connection to datomic instance: " uri)
    (d/create-database uri)
    (d/transact
      (d/connect uri)
      schemas)
    (seed-db)
    (d/connect uri)))


(defn- drop-connection [conf conn]
  (let [uri (:database-uri conf)]
    (info "Dropping connection to datomic instance: " uri)
    (d/delete-database uri)
    (d/release conn)))


(defstate conn
          :start (new-connection env)
          :stop (drop-connection env conn))
