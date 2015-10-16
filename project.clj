(defproject clj-options "0.1.0"
  :description "Vanilla options handling in Clojure"
  :url "https://github.com/anonymoose/clj-options"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {
                   :dependencies [
                                  [midje "1.7.0"]
                                  [org.clojure/data.csv "0.1.3"]
                                  ]}}
  :dependencies [
                 [org.clojure/clojure "1.7.0"]
                 [clj-time "0.11.0"]
                 [com.pvs.joptions/joptions "0.0.1"]
                 ]
  :test-paths ["test"]
  )
