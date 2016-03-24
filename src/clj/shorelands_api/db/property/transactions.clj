(ns shorelands-api.db.property.transactions
  (:require [datomic.api :as d]
            [shorelands-api.db.core :refer [conn]]
            [shorelands-api.db.property.core :refer [property->dbproperty]]
            [shorelands-api.db.property.queries :refer [get-properties]]))


;; Database functions
(def construct-property-map-fn
  "Returns a map that could be added in a tx to create
  a property, returns nil if property exists (name)"
  #db/fn {:lang   :clojure
          :params [db property]
          :code   (when-not (seq (d/q '[:find ?e
                                        :in $ ?name
                                        :where [?e :property/name ?name]]
                                      db (:property/name property)))
                    property)})

(def create-property-fn
  "Returns tx data to create a new property"
  #db/fn {:lang   :clojure
          :params [db property]
          :code   (if-let [prop (d/invoke db :property/construct-map db property)]
                    [prop]
                    (throw (ex-info "Validation failed"
                                    {:property/name ["Already exists"]})))})


@(d/transact conn [{:db/id    (d/tempid :db.part/user)
                    :db/ident :property/construct-map
                    :db/fn    construct-property-map-fn}])



@(d/transact conn [{:db/id    (d/tempid :db.part/user)
                    :db/ident :property/create
                    :db/fn    create-property-fn}])

;; Api Functions

(defn create-property [property]
  (let [prop (-> property
                 (property->dbproperty)
                 (merge {:db/id (d/tempid :db.part/user)}))]
    @(d/transact conn [[:property/create prop]])))


(defn update-property [property]
  (let [prop (property->dbproperty property)]
    @(d/transact conn [prop])
    (get-properties)))