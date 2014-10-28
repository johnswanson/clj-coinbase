(defproject clj-coinbase "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:dependencies [[http-kit.fake "0.2.1"]
                                  [org.clojure/tools.namespace "0.2.7"]]
                   :source-paths ["dev"]
                   :resource-paths ["test/resources"]}}
  :plugins [[lein-environ "1.0.0"]]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [environ "1.0.0"]
                 [http-kit "2.1.18"]])
