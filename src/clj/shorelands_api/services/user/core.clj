(ns shorelands-api.services.user.core
		(:require [clojure.java.io :as io]
				  [ring.util.http-response :refer :all]
				  [compojure.api.sweet :refer :all]
				  [schema.core :as s]
				  [shorelands-api.middleware :refer [wrap-cors]]
				  [shorelands-api.db.user.queries :as q]
				  [shorelands-api.db.user.transactions :as t]))


(s/defschema User
  {:id      	 Long
   :email	     String
   :username     String })

(def user-service
  (context "/api" []

	  :tags ["User"]

	  (GET "/users" 		[]
			:return 		[User]
			:summary 		"returns the list of users"
			:middleware 	[wrap-cors]
			(ok (q/get-users)))

	  ;(GET* "/user/:id" 	[]
		;	:return 		User
		;	:path-params 	[id :- Long]
		;	:summary 		"returns the user with a given id"
		;	(ok (first (db/get-user {:id id}))))


	  (POST "/user"		[]
			 :return		User
			 :middleware	[wrap-cors]
			 :body-params	[user :- User]
			 :summary		"Create a new user with a User object"
			 (ok (t/add-user user)))

	  ;(POST* "/user" []
	  ;       :return Long
	  ;       :body-params [user :- User]
	  ;       :summary "creates a new user record."
	  ;       (ok (db/create-user-account! user)))

	  ;(DELETE* "/user" []
		;	   :return 		Long
		;	   :body-params [id :- String]
		;	   :summary 	"deletes the user record with the given id."
	  ;(ok (db/delete-user! {:id id})))
			))


