(ns shorelands-api.db.property.queries
  (:require [datomic.api :as d :refer [pull]]
            [shorelands-api.config :refer [env]]
            [shorelands-api.db.core :refer [conn]]
            [shorelands-api.db.property.core :refer [dbproperty->property]]))


(defn page-fn
  [page limit properties]
  (let [num-properties (count properties)
        partitions (partition (or limit num-properties) properties)
        page (or page 0)]
    (if (< page (count partitions))
      (nth partitions page)
      (throw (ex-info "Page index out of bound"
                      {:cause :ValidationFailed
                       :error {:query/page "OutOfBound"}})))))


(defn get-properties
  ([]
   (get-properties {}))
  ([transforms]
   (let [{:keys [sort page limit]} transforms
         properties (d/q '[:find [(pull ?pid [*]) ...]
                           :where [?pid :property/name _]]
                         (d/db conn))
         sort-fn (keyword (or sort :id))]
     (->> properties
          (map dbproperty->property)
          (sort-by sort-fn)
          (page-fn page limit)))))
