(defproject clojurewerkz/cyclist "1.1.0-SNAPSHOT"
  :description "Tiny library that detects cyclic dependencies between any named entities"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clojurewerkz/support "0.20.0"]]
  :profiles {:1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0-beta2"]]}
             :master {:dependencies [[org.clojure/clojure "1.6.0-master-SNAPSHOT"]]}
             :dev {:resource-paths ["test/resources"]
                   :plugins [[codox "0.6.6"]]
                   :codox {:sources ["src/clojure"]
                           :output-dir "doc/api"}}}
  :aliases {"all" ["with-profile" "dev:dev,1.4:dev,1.6:dev,master"]}
  :repositories {"sonatype" {:url "http://oss.sonatype.org/content/repositories/releases"
                             :snapshots false
                             :releases {:checksum :fail}}
                 "sonatype-snapshots" {:url "http://oss.sonatype.org/content/repositories/snapshots"
                                       :snapshots true
                                       :releases {:checksum :fail :update :always}}}
  :javac-options      ["-target" "1.6" "-source" "1.6"]
  :jvm-opts           ["-Dfile.encoding=utf-8"]
  :source-paths       ["src/clojure"]
  :java-source-paths  ["src/java"])
