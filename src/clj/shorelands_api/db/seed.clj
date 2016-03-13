(ns shorelands-api.db.seed
  (:require [crypto.password.bcrypt :as password]
            [datomic.api :as d]
            [shorelands-api.config :refer [env]]))

(defn seed-db []
  (let [gid (d/tempid :db.part/user)
        gid2 (d/tempid :db.part/user)
        conn (d/connect (:database-uri env))]
    (d/transact
      conn
      [{:db/id            gid
        :group/name       "Staff"
        :group/permission "Admin"}
       {:db/id            gid2
        :group/name       "Mod"
        :group/permission "Moderator"}
       {:db/id         (d/tempid :db.part/user)
        :user/name     "Yannick"
        :user/email    "yannick.sealy08@gmail.com"
        :user/password (password/encrypt "password")
        :user/group    [gid gid2]
        :user/status   :user.status/active}
       {:db/id         (d/tempid :db.part/user)
        :user/name     "Julian"
        :user/email    "jjelfs@gmail.com"
        :user/password (password/encrypt "jeff")
        :user/group    [gid gid2]
        :user/status   :user.status/active}])))