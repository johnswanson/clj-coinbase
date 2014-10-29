(ns user
  (:require [org.httpkit.client :as c]
            [org.httpkit.fake :refer :all]
            [clojure.test :refer :all]
            [clj-coinbase.core :refer :all]
            [clj-coinbase.http :refer :all]
            [clojure.java.io :as io]
            [clojure.java.javadoc :refer [javadoc]]
            [clojure.pprint :refer [pprint]]
            [clojure.reflect :refer [reflect]]
            [clojure.repl :refer [apropos dir doc find-doc pst source]]
            [clojure.set :as set]
            [clojure.string :as str]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [environ.core :refer [env]]))

(def k (:coinbase-api-key env))
(def s (:coinbase-api-secret env))
