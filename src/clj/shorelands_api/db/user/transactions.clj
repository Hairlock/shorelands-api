(ns shorelands-api.db.user.transactions
  (:require [buddy.hashers :as hashers]
            [datomic.api :as d]
            [shorelands-api.db.core :refer [conn]]
            [shorelands-api.db.user.queries :as q]))


(defn add-user [user]
  (let [{:keys [name email password]} user
        uid (d/tempid :db.part/user)]
    @(d/transact conn [{:db/id         uid
                        :user/name     name
                        :user/email    email
                        :user/password (hashers/encrypt password)
                        :user/status   :user.status/active
                        :user/group    (q/get-user-group "Staff")}])))


