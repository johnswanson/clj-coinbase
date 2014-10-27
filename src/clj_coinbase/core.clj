(ns clj-coinbase.core
  (:require [clj-http.client :as c]))

(def default-config
  {:version 1
   :key nil
   :secret nil
   :auth-method :api-key})

(defn base-url [client]
  (case (:version client)
    1 "https://api.coinbase.com/v1/"
    "https://api.coinbase.com/v1/"))

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
  {:nonce (atom 0)})

(defn connection [client]
  (merge client default-config))
