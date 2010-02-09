(defproject clj-json "0.1.0-SNAPSHOT"
  :description "Fast JSON encoding and decoding for Clojure via the Jackson library."
  :url "http://github.com/mmcgrana/clj-json"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-fork "true"
  :dependencies [[org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]
                 [org.codehaus.jackson/jackson-core-asl "1.4.0"]]
  :dev-dependencies [[lein-clojars "0.5.0-SNAPSHOT"]
                     [clj-unit "0.1.0-SNAPSHOT"]
                     [lein-javac "0.0.2-SNAPSHOT"]])