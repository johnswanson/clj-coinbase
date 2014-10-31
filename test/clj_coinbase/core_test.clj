(ns clj-coinbase.core-test
  (:require [org.httpkit.client :as c]
            [org.httpkit.fake :refer :all]
            [clojure.test :refer :all]
            [clj-coinbase.core :refer :all]
            [environ.core :refer [env]]))

(comment

(deftest test-with-default
  (testing "Defaults"
    (let [client (client)]
      (is (= (:defaults client) {}))
      (is (= (:defaults (with-default client :key :value)) {:key :value}))
      (is (= (:defaults (with-default client :key :value :other :value))
             {:key :value :other :value})))))

(def now-atom (atom 0))
(def key "fake-key")
(def secret "fake-secret")

(deftest requests
  (testing "Nonce updates at correct time"
    (binding [*now* (fn [] (let [current-val @now-atom]
                            (swap! now-atom inc)
                            current-val))]
      (let [client (-> (client) (with-api-key key secret))]
        (is ( = @(:nonce client) 0))
        (make-request! client "test" :get {})
        (is ( = @(:nonce client) 1))))))

 )
