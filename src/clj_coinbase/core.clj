(ns clj-coinbase.core
  (:require [pandect.core :refer [sha256-hmac]]
            [clj-time.coerce :refer [to-long]]
            [clj-time.core :refer [now]]
            [cheshire.core :refer [generate-string]]
            [org.httpkit.client :as c]
            [ring.util.codec :refer [form-encode]]))

(defn new-nonce [] (to-long (now)))

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
  {:nonce (atom (new-nonce))
   :version 1
   :key nil
   :secret nil
   :auth-method nil})

(defn with-header [req-options & kvs]
  (update-in req-options [:headers] #(apply assoc % kvs)))

(defn nonce [client] (deref (:nonce client)))

(defn full-url
  "URL (including query params for GET requests). This is probably stupid and
  definitely fragile."
  [request]
  (if (seq (:query-params request))
    (str (:url request) "?" (form-encode (:query-params request)))
    (:url request)))

(defn signature [client req]
  (sha256-hmac (str (nonce client)
                    (full-url req)
                    (:body req))
               (:secret client)))

(defn sign [req client]
  (with-header req
    "ACCESS_SIGNATURE" (signature client req)
    "ACCESS_NONCE" (nonce client)))

(defn base-url [client]
  (case (:version client)
    1 "https://api.coinbase.com/v1"))

(defn request [client url method params]
  (merge {:url (format "%s/%s" (base-url client) url)
          :method method
          :headers {"ACCESS_KEY" (:key client)}
          :user-agent "clj"}
         (if (= method :get)
           {:query-params (apply hash-map params)}
           {:body (generate-string params)})))

(defn update-nonce! [client]
  (swap! (:nonce client) (constantly (new-nonce))))

(defn make-request! [client url method & params]
  (update-nonce! client)
  (let [resp @(c/request (sign (request client url method params) client))]
    resp))
