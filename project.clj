(defproject org.clojars.thnetos/clj-json "0.3.2-SNAPSHOT"
  :description "Fast JSON encoding and decoding for Clojure via the Jackson library. Forked to add SMILE support"
  :url "http://github.com/dakrone/clj-json"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-fork "true"
  :dependencies
    [[org.clojure/clojure "1.2.0"]
     [org.clojure/clojure-contrib "1.2.0"]
     [org.codehaus.jackson/jackson-core-asl "1.6.3"]
     [org.codehaus.jackson/jackson-smile "1.6.3"]]
  :dev-dependencies
    [[org.clojars.mmcgrana/lein-javac "1.2.1"]])
