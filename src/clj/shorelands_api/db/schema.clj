(ns shorelands-api.db.schema
  (:require [datomic-schema.schema :as s]))

(defn parts []
  [(s/part "shorelands")])

(defn create-schemas []
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


(def schemas
  (concat
    (s/generate-parts (parts))
    (s/generate-schema (create-schemas) {:index-all? true})))
