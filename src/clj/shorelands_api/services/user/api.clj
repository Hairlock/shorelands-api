(ns shorelands-api.services.user.api
  (:require [clojure.java.io :as io]
            [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [shorelands-api.middleware :refer [wrap-cors token-auth authentication]]
            [shorelands-api.db.user.queries :as q]
            [shorelands-api.db.user.transactions :as t]))


(s/defschema User
  {:id       Long
   :email    String
   :name String})

(def user-service
  (context "/api" []

           :tags ["User"]

           (GET "/users" []
                :return [User]
                :summary "returns the list of users"
                :middleware [wrap-cors token-auth authentication]
                (ok (q/get-users)))   

           (POST "/user" []
                 :return User
                 :middleware [wrap-cors]
                 :body-params [user :- User]
                 :summary "Create a new user with a User object"
                 (ok (t/add-user user)))
           ))


