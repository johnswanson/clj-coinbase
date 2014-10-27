(defproject clj-coinbase "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[clj-http-fake "0.7.8"]
                                  [org.clojure/tools.namespace "0.2.7"]]
                   :source-paths ["dev"]
                   :resource-paths ["test/resources"]}}
  :plugins [[lein-environ "1.0.0"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [environ "1.0.0"]
                 [clj-http "1.0.0"]])
