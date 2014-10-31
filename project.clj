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
  :dependencies [[org.clojure/clojure "1.7.0-alpha3"]
                 [org.clojure/tools.macro "0.1.5"]
                 [environ "1.0.0"]
                 [http-kit "2.1.18"]
                 [pandect "0.4.1"]
                 [clj-time "0.8.0"]
                 [cheshire "5.3.1"]
                 [ring/ring-codec "1.0.0"]])
