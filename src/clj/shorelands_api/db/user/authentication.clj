(ns shorelands-api.db.user.authentication
  (:require [shorelands-api.config :refer [env]]
            [clj-time.core :as time]
            [buddy.sign.jws :as jws]
            [datomic.api :as d]
            [shorelands-api.db.core :refer [conn]]
            [shorelands-api.db.user.queries :refer [get-users]]))


(defn create-token
  "Creates json web token containing email, id, permissions
  and expiration time. Valid for 60 minutes"
  [user]
  (let [stringify-user (-> user
                           (update-in [:email] str)
                           (update-in [:name] str)
                           (assoc :exp (time/plus (time/now) (time/minutes 60))))
        token (select-keys stringify-user [:email :name :id :exp])]
    (jws/sign token (:auth-key env) {:alg :hs512})))


(defn update-user-token
  [user]
  (let [new-token (create-token user)]
    @(d/transact conn [{:db/id (:id user)
                        :user/token new-token}])))