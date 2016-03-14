(ns shorelands-api.markdown.core
  (:require [markdown.core :refer [md-to-html-string]]
            [markdown.transformers :refer [transformer-vector]]))


(defn- escape-html [text state]
  (let [sanitized-text (clojure.string/escape text
                                              {\& "&amp;"
                                               \< "&lt;"
                                               \> "&gt;"
                                               \" "&quot;"
                                               \' "&#39;"})]
  [sanitized-text state]))


(defn safe-md-to-html [md]
  (md-to-html-string md
                     :replacement-transformers
                     (cons escape-html transformer-vector)))










