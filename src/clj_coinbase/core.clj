(ns clj-coinbase.core
  (:require [clj-coinbase.client :as client]
            [cheshire.core :refer [parse-string]]))

(defn route [verb url handler]
  (fn [client & args]
    (client/go client url verb (apply hash-map args) handler)))

(defmacro defroute
  [name verb url arg-list response]
  `(def ~name
     (route ~verb ~url (fn ~arg-list ~response))))

(defroute accounts :get "accounts"
  [resp]
  (parse-string resp true))
