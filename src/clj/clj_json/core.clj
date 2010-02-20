(ns clj-json.core
  (:import (clj_json JsonExt)
           (org.codehaus.jackson JsonFactory JsonParser)
           (java.io StringWriter StringReader BufferedReader))
  (:use (clojure.contrib [def :only (defvar-)])))

(defvar- #^JsonFactory factory (JsonFactory.))

(defn generate-string
  {:tag String
   :doc "Returns a JSON-encoding String for the given Clojure object."}
  [obj]
  (let [sw        (StringWriter.)
        generator (.createJsonGenerator factory sw)]
    (JsonExt/generate generator obj)
    (.flush generator)
    (.toString sw)))

(defn parse-string
  "Returns the Clojure object corresponding to the given JSON-encoded string."
  [string]
  (JsonExt/parse (.createJsonParser factory (StringReader. string)) true nil))

(defn- parsed-seq* [#^JsonParser parser]
  (let [eof (Object.)]
    (lazy-seq
      (let [elem (JsonExt/parse parser true eof)]
        (if-not (identical? elem eof)
          (cons elem (parsed-seq* parser)))))))

(defn parsed-seq [#^BufferedReader reader]
  "Returns a lazy seq of Clojure objects corresponding to the JSON read from
  the given reader. The seq continues until the end of the reader is reached."
  (parsed-seq* (.createJsonParser factory reader)))
