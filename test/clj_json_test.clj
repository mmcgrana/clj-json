(ns clj-json-test
  (:require clj-json)
  (:import (java.io StringReader BufferedReader))
  (:use (clj-unit core)))

(deftest "generate-string/parse-string round trip"
  (let [struct {"int" 3 "long" 52001110638799097 "bigint" 9223372036854775808
                "double" 1.23 "boolean" true "nil" nil "string" "string"
                "vec" [1 2 3] "map" {"a" "b"} "list" (list "a" "b")}]
    (assert= struct (clj-json/parse-string (clj-json/generate-string struct)))))

(deftest "generate-string keyword coercion"
  (assert= {"foo" "bar"}
           (clj-json/parse-string (clj-json/generate-string {:foo "bar"}))))

(deftest "parsed-seq"
  (let [br (BufferedReader. (StringReader. "1\n2\n3\n"))]
    (assert= (list 1 2 3) (clj-json/parsed-seq br))))