(defproject clj-json "0.3.0-SNAPSHOT"
  :description "Fast JSON encoding and decoding for Clojure via the Jackson library."
  :url "http://github.com/mmcgrana/clj-json"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-fork "true"
  :dependencies [[org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]
                 [org.codehaus.jackson/jackson-core-asl "1.5.0"]]
  :dev-dependencies [[org.clojars.mmcgrana/lein-clojars "0.5.0"]
                     [lein-javac "1.2.0-SNAPSHOT"]])
