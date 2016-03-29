(ns shorelands-api.db.schema
  (:require [datomic-schema.schema :as s]))

(defn parts []
  [(s/part "shorelands")])

(defn create-schemas []
  [(s/schema property
             (s/fields
               [name :string :unique-value]
               [description :string "Description of the property"]
               [location :string "Location of the property"]
               [propertytype :string "Type of property"]
               [sellprice :long]
               [rentprice :long]
               [amenities :string "List of amenities"]))
   (s/schema amenity
             (s/fields
               [text :string "Amenity text"]))
   (s/schema user
             (s/fields
               [name :string :unique-value]
               [email :string :unique-value]
               [password :string "Hashed password string"]
               [token :string "Authentication token for api validation"]
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
