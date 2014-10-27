(ns clj-coinbase.core-test
  (:require [clj-http.client :as c]
            [clj-http.fake :refer :all]
            [clojure.test :refer :all]
            [clj-coinbase.core :refer :all]))

(deftest account-changes-json
  (testing "Account changes"
    (with-fake-routes
      {#"https://api.coinbase.com/.+" (fn [req]
                                        {:status 200
                                         :headers {}
                                         :body (str req)})})))
