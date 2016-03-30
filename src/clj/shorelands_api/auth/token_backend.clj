(ns shorelands-api.auth.token-backend
  (:require [shorelands-api.config :refer [env]]
            [buddy.auth.backends.token :refer [jws-backend]]))


(def token-backend
  (jws-backend {:secret (:auth-key env)
                :options {:alg :hs512}}))
