(ns clj-json.core
  (:import (clj_json JsonExt)
           (org.codehaus.jackson JsonFactory JsonParser JsonParser$Feature)
           (java.io Writer StringWriter StringReader BufferedReader))
  (:use (clojure [walk :only (postwalk)]
                 [string :only (join)])))

(def ^{:private true :tag JsonFactory} factory
  (doto (JsonFactory.)
    (.configure JsonParser$Feature/ALLOW_UNQUOTED_CONTROL_CHARS true)))

(def ^{:dynamic true} *coercions* nil)

(defn generate-to-writer
  "Attempts to write a json-encoded string for the given Object directly to the given writer."
  [obj #^Writer writer]
  (let [generator (.createJsonGenerator factory writer)]
    (JsonExt/generate generator *coercions* obj)
    (.flush generator)))

(defn generate-string
  "Returns a JSON-encoding String for the given Clojure object."
  {:tag String}
  [obj]
  (let [sw        (StringWriter.)]
    (generate-to-writer obj sw)
    (.toString sw)))

(defn parse-string
  "Returns the Clojure object corresponding to the given JSON-encoded string."
  [string & [keywords]]
  (JsonExt/parse
    (.createJsonParser factory (StringReader. string))
    true (or keywords false) nil))

(defn- parsed-seq* [^JsonParser parser keywords]
  (let [eof (Object.)]
    (lazy-seq
      (let [elem (JsonExt/parse parser true keywords eof)]
        (if-not (identical? elem eof)
          (cons elem (parsed-seq* parser keywords)))))))

(defn parsed-seq
  "Returns a lazy seq of Clojure objects corresponding to the JSON read from
  the given reader. The seq continues until the end of the reader is reached."
  [^BufferedReader reader & [keywords]]
  (parsed-seq* (.createJsonParser factory reader) (or keywords false)))
