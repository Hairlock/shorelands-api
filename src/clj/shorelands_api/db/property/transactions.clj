;(ns shorelands-api.db.property.transactions
;  (:require [datomic.api :as d]
;            [shorelands-api.config :refer [env]]
;            [shorelands-api.db.core :refer [conn]]
;            [shorelands-api.db.property.queries :refer [get-properties]]))