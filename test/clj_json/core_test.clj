(ns clj-json.core-test
  (:use clojure.test)
  (:require [clj-json.core :as json])
  (:import (java.io StringReader BufferedReader)))

(deftest test-string-round-trip
  (let [obj {"int" 3 "long" 52001110638799097 "bigint" 9223372036854775808
             "double" 1.23 "boolean" true "nil" nil "string" "string"
             "vec" [1 2 3] "map" {"a" "b"} "list" (list "a" "b")}]
    (is (= obj (json/parse-string (json/generate-string obj))))))

(deftest test-keyword-coercion
  (is (= {"foo" "bar"}
         (json/parse-string (json/generate-string {:foo "bar"})))))

(deftest parsed-seq
  (let [br (BufferedReader. (StringReader. "1\n2\n3\n"))]
    (is (= (list 1 2 3) (json/parsed-seq br)))))
