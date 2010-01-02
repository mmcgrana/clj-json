(ns clj-json
  (:import (clj_json JsonExt)
           (org.codehaus.jackson JsonFactory JsonParser)
           (java.io StringWriter StringReader BufferedReader))
  (:use (clojure.contrib [def :only (defvar-)])))

(defvar- #^JsonFactory factory (JsonFactory.))

(defn generate-string [obj]
  (let [sw        (StringWriter.)
        generator (.createJsonGenerator factory sw)]
    (JsonExt/generate generator obj)
    (.flush generator)
    (.toString sw)))

(defn parse-string [string]
  (JsonExt/parse (.createJsonParser factory (StringReader. string)) true nil))

(defn- parsed-seq* [#^JsonParser parser]
  (let [eof (Object.)]
    (lazy-seq
      (let [elem (JsonExt/parse parser true eof)]
        (if-not (identical? elem eof)
          (cons elem (parsed-seq* parser)))))))

(defn parsed-seq [#^BufferedReader reader]
  (parsed-seq* (.createJsonParser factory reader)))