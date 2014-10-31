(ns clj-coinbase.accounts
  (:require [clj-coinbase.core :refer [defroute]]
            [cheshire.core :refer [parse-string]]))

(def parse #(parse-string % true))

(defroute accounts :get "accounts"
  [resp]
  (parse resp))

(defroute balance :get "accounts/:account-id/balance"
  [resp]
  (parse resp))
