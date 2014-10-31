(ns clj-coinbase.request
  (:require [cheshire.core :refer [generate-string]]
            [org.httpkit.client :as c]
            [ring.util.codec :refer [form-encode]]))

(defn fetch [req-obj]
  (deref (c/request req-obj)))

(defn with-header [req-options & kvs]
  (update-in req-options [:headers] #(apply assoc % kvs)))

(defn full-url
  "URL (including query params for GET requests). This is probably stupid and
  definitely fragile."
  [request]
  (if (seq (:query-params request))
    (format "%s?%s" (:url request) (form-encode (:query-params request)))
    (:url request)))

(defn request [client url method params]
  (merge {:url url
          :method method
          :headers {"ACCESS_KEY" (:key client)}
          :user-agent "clj"}
         (if (= method :get)
           {:query-params params}
           {:body (generate-string params)})))

(def ->regex
  (comp (map first)
        (map str)
        (map re-pattern)))

(defn value-of [expression params]
  (let [lookup-keyword (keyword (apply str (rest (str expression))))]
    (params lookup-keyword)))

(defn replace-in-url [url parameters]
  (transduce ->regex
             (fn [url & [exp]]
               (if exp
                 (clojure.string/replace url exp (value-of exp parameters))
                 url))
             url
             parameters))
