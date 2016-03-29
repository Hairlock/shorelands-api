(ns shorelands-api.db.user.queries
  (:require [datomic.api :as d :refer [pull]]
			[shorelands-api.db.core :refer [conn]]))



(defn get-user-group [name]
  (->> (d/q '[:find ?e
			  :in $ ?name
			  :where [?e :group/name ?name]]
		  	(d/db conn)
			name)
	   ffirst))

(defn get-group-permission
  [{gid :db/id}]
  (let [permission  (d/q '[:find ?perm
						   :in $ ?eid
						   :where [?eid :group/permission ?perm]]
						 conn gid)]
	(first permission)))


(defn format-users
  [{id :db/id name :user/name email :user/email groups :user/group}]
  {:id id
   :name name
   :email    email
   ;:groups   (flatten (map get-group-permission groups))
   })

(defn get-users []
  (let [users (d/q '[:find [(pull ?uid [*]) ...]
					 :where [?uid :user/name _]]
				   (d/db conn))]
	(map format-users users)))







