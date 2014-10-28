(ns clj-coinbase.core-test
  (:require [org.httpkit.client :as c]
            [org.httpkit.fake :refer :all]
            [clojure.test :refer :all]
            [clj-coinbase.core :refer :all]))

(deftest account-changes-json
  (testing "Account changes"
    (with-fake-http [#"^https://api.coinbase.com/" (fn [orig-fn opts callback]
                                                     {:status 418})]
      (is (= 418 (:status (deref (c/get "https://api.coinbase.com/test"))))))))
