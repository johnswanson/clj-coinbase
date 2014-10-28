(ns clj-coinbase.core
  (:require [org.httpkit.client :as c]))

(defn with-api-key [client key secret]
  (assoc client
    :key key
    :secret secret
    :auth-method :api-key))

(defn with-token [client token]
  (assoc client
    :token token
    :auth-method :token))

(defn with-account-id [client id]
  (assoc client :account-id id))

(defn client []
  {:nonce (atom 0)
   :version 1
   :key nil
   :secret nil
   :auth-method nil})
