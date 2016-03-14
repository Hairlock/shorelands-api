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
      [
       {:db/id            gid
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
        :user/status   :user.status/active}
       {:db/id                 (d/tempid :db.part/user)
        :property/name         "Rainbow Villas"
        :property/location     "Goodwood Park"
        :property/description  "This is a 5,000 square foot luxury 4
                               bedroom 4 bathroom villa in a gated
                               community called Rainbow Villas at Goodwood
                               Park."
        :property/propertytype "house"
        :property/sellprice    5500000.00
        :property/rentprice    25800.00
        :property/amenities     "Premium end unit with fantastic ocean and city views,
                                  Floor area climate-controlled"}])))