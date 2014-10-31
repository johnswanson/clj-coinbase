(ns clj-coinbase.client
  (:require [clj-time.core :refer [now]]
            [clj-time.coerce :refer [to-long]]
            [pandect.core :refer [sha256-hmac]]
            [clj-coinbase.request :as request]))

(def ^:dynamic *now*
  "This is solely for testing, e.g. (binding [*now* (constantly 0)] ...)"
  now)

(defn update-nonce! [client]
  (swap! (:nonce client) (constantly (to-long (*now*)))))

(defn with-api-key [client key secret]
  (assoc client
    :key key
    :secret secret
    :auth-method :api-key))

(defn with-token [client token]
  (assoc client
    :token token
    :auth-method :token))

(defn with-default [client & keyvals]
  (assoc client
    :defaults
    (reduce (fn [m [k v]]
              (assoc m k v))
            (:defaults client)
            (partition 2 keyvals))))

(defn client []
  {:nonce (atom nil)
   :version 1
   :key nil
   :secret nil
   :auth-method nil
   :token nil
   :defaults {}})

(defn- nonce [client] (deref (:nonce client)))

(defn- signature [client req]
  (sha256-hmac (str (nonce client)
                    (request/full-url req)
                    (:body req))
               (:secret client)))

(defn- sign [client req]
  (request/with-header req
    "ACCESS_SIGNATURE" (signature client req)
    "ACCESS_NONCE" (nonce client)))

(defn base-url [client]
  (case (:version client)
    1 "https://api.coinbase.com/v1"))

(defn url [client resource]
  (format "%s/%s" (base-url client) resource))

(defn request [client resource verb params]
  (request/fetch
   (sign
    client
    (request/request client (url client resource) verb params))))

(defn error? [resp]
  nil)

(defn- parameters
  "This function returns a map with the correct parameters to use for the call"
  [client argmap]
  (merge (:defaults client) argmap))

(defn go [client url verb ps handler]
  (update-nonce! client)
  (let [params (parameters client ps)
        resp (request client url verb params)]
    (if-let [err (error? resp)]
      err
      (handler (:body resp)))))
