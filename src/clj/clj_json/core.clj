(ns clj-json.core
  (:import (clj_json JsonExt)
           (org.codehaus.jackson JsonFactory JsonParser JsonParser$Feature)
           (java.io StringWriter StringReader BufferedReader))
  (:use (clojure [walk :only (postwalk)]
                 [string :only (join)])))

(def ^{:private true :tag JsonFactory} factory
  (doto (JsonFactory.)
    (.configure JsonParser$Feature/ALLOW_UNQUOTED_CONTROL_CHARS true)))

(def *coercions* {clojure.lang.IPersistentSet vec
                  clojure.lang.Keyword (fn [x] (join "/" (remove nil? ((juxt namespace name) x))))})

(defn- coerce [obj]
  (postwalk (fn [x]
              (if-let [coercion (seq (filter #(instance? (key %) x) *coercions*))]
                ((-> coercion first val) x)
                x)) obj))

(defn generate-string
  "Returns a JSON-encoding String for the given Clojure object."
  {:tag String}
  [obj]
  (let [sw        (StringWriter.)
        generator (.createJsonGenerator factory sw)]
    (JsonExt/generate generator (coerce obj))
    (.flush generator)
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
