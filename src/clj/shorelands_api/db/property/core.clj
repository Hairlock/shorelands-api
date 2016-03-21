(ns shorelands-api.db.property.core
  (:require [clojure.set :refer [rename-keys]]))


(def app-property [:id :name :location :description :propertytype :sellprice :rentprice :amenities])

(def db-property [:db/id :property/name :property/location :property/description :property/propertytype
                  :property/sellprice :property/rentprice :property/amenities])

(defn- format-property
  [property to-type]
  (case to-type
    :prop->dbprop (rename-keys property (zipmap app-property db-property))
    :dbprop->prop (rename-keys property (zipmap db-property app-property))))

(defn property->dbproperty
  [property]
  (format-property property :prop->dbprop))

(defn dbproperty->property
  [property]
  (format-property property :dbprop->prop))
