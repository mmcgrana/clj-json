(defproject clj-json "0.4.2"
  :description "Fast JSON encoding and decoding for Clojure via the Jackson library."
  :url "http://github.com/mmcgrana/clj-json"
  :source-path "src/clj"
  :java-source-path "src/jvm"
  :javac-fork "true"
  :dependencies
    [[org.clojure/clojure "1.2.0"]
     [org.codehaus.jackson/jackson-core-asl "1.5.0"]]
  :dev-dependencies
    [[org.clojars.mmcgrana/lein-javac "1.2.1"]])
