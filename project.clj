(defproject clj-json "0.5.2"
  :description "Fast JSON encoding and decoding for Clojure via the Jackson library."
  :url "http://github.com/mmcgrana/clj-json"
  :source-paths ["src/clj"]
  :java-source-paths ["src/jvm"]
  :javac-fork "true"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.codehaus.jackson/jackson-core-asl "1.9.9"]])
