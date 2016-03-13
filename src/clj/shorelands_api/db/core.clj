(ns shorelands-api.db.core
  (:require
    [crypto.password.bcrypt :as password]
    [datomic.api :as d]
    [datomic-schema.schema :as s]
    [clojure.tools.logging :refer [info]]
    [shorelands-api.config :refer [env]]
    [shorelands-api.db.schema :refer [schemas]]
    [shorelands-api.db.seed :refer [seed-db]]
    [mount.core :as mount :refer [defstate]]
    ))

(defn parts []
  [(s/part "shorelands")])

(defn schema []
  [(s/schema user
             (s/fields
               [name :string :indexed :unique-value]
               [email :string :indexed :unique-value]
               [password :string "Hashed password string"]
               [status :enum [:pending :active :inactive :cancelled]]
               [group :ref :many]))
   (s/schema group
             (s/fields
               [name :string]
               [permission :string :many]))])

(defn setup-db []
  (let [url "datomic:mem://shorelandsdb"]
    (d/delete-database url)
    (d/create-database url)
    (d/transact
      (d/connect url)
      (concat
        (s/generate-parts (parts))
        (s/generate-schema (schema) {:index-all? true})))))


(defn- new-connection [conf]
  (let [uri (:database-uri conf)]
    (info "Connection to datomic instance: " uri)
    (d/create-database uri)
    (d/transact
      (d/connect uri)
      schemas
      )
    (seed-db)
    (d/connect uri)))

(defn- drop-connection [conf conn]
  (let [uri (:database-uri conf)]
    (info "Dropping connection to datomic instance: " uri)
    (d/delete-database uri)))


(defstate conn
          :start (new-connection env)
          :stop (drop-connection env conn))

;(mount/stop #'conn)
;(mount/start #'conn)

;(defn start []
;  (setup-db)
;  (seed-db))
;
;(defn stop []
;  (d/delete-database uri))

;(start)


;(def conn (d/connect uri))

