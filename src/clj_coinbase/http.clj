(ns clj-coinbase.http
  "Composable requests"
  (:require [org.httpkit.client :as c]
            [clj-time.coerce :refer [to-long]]
            [clj-time.core :refer [now]]))
