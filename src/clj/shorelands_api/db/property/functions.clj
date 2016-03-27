(ns shorelands-api.db.property.functions
  (:require [datomic.api :as d]))


;; Database functions
(def construct-property-map-fn
  "Returns a map that could be added in a tx to create
  a property, returns nil if property exists (name)"
  (d/function '{:lang   :clojure
                :params [db property]
                :code   (when-not (seq (d/q '[:find ?e
                                              :in $ ?name
                                              :where [?e :property/name ?name]]
                                            db (:property/name property)))
                          property)}))

(def create-property-fn
  "Returns tx data to create a new property"
  (d/function '{:lang   :clojure
                :params [db property]
                :code   (if-let [prop (d/invoke db :property/construct-map db property)]
                          [prop]
                          (throw (ex-info "Validation failed"
                                          {:cause :ValidationFailed
                                           :error {:property/name "Already exists"}})))}))

(defn add-property-fns [conn]
  @(d/transact conn [{:db/id    (d/tempid :db.part/user)
                      :db/ident :property/construct-map
                      :db/fn    construct-property-map-fn}])



  @(d/transact conn [{:db/id    (d/tempid :db.part/user)
                      :db/ident :property/create
                      :db/fn    create-property-fn}]))