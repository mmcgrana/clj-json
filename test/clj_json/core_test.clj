(ns clj-json.core-test
  (:use clojure.test)
  (:require [clj-json.core :as json])
  (:import (java.io StringReader BufferedReader File FileWriter)
           (java.util HashMap)))

(deftest test-string-round-trip
  (let [obj {"int" 3 "long" 52001110638799097 "bigint" 9223372036854775808N
             "double" 1.23 "boolean" true "nil" nil "string" "string"
             "vec" [1 2 3] "map" {"a" "b"} "list" (list "a" "b")
             "hmap" (HashMap. {"a" "b"})}]
    (is (= obj (json/parse-string (json/generate-string obj))))))

(deftest test-generate-accepts-float
  (is (= "3.14" (json/generate-string (float 3.14)))))

(deftest test-generate-accepts-ratios
  (is (= "0.5" (json/generate-string 2/4))))

(deftest test-key-coercion
  (is (= {"foo" "bar" "1" "bat" "2" "bang" "3" "biz"}
         (json/parse-string
           (json/generate-string
            {:foo "bar" 1 "bat" (long 2) "bang" (bigint 3) "biz"})))))

(deftest test-write-to-tmp
  (let [f           (File/createTempFile "clj-json-test" "dat")
        writer      (FileWriter. f)
        test-object {"a" "cow" "jumped" "over" "the" 88}]
    (try
      (json/generate-to-writer test-object writer)
      (is (= (slurp f) (json/generate-string test-object)))
      (finally (.close writer)))))

(deftest test-keywords
  (is (= {:foo "bar" :bat 1}
         (json/parse-string
           (json/generate-string {:foo "bar" :bat 1})
           true))))

(deftest test-parsed-seq
  (let [br (BufferedReader. (StringReader. "1\n2\n3\n"))]
    (is (= (list 1 2 3) (json/parsed-seq br)))))

(deftest test-set-coercion
  (is (= {:foo ["bar" "bang"]}
         (json/parse-string
          (json/generate-string {:foo #{"bar" "bang"}})
          true))))

(deftest test-redefine-coercions
  (binding [clj-json.core/*coercions* {clojure.lang.PersistentHashSet
                                       (fn [x] (reduce (fn [acc x] (assoc acc x true)) {} x))}]
    (is (= {"foo" {"bang" true, "bar" true}}
           (json/parse-string
            (json/generate-string {"foo" #{"bar" "bang"}}))))))

(deftest test-redefine-coercions-with-nil
  (binding [clj-json.core/*coercions* {clojure.lang.PersistentHashSet
                                       (fn [x] (reduce (fn [acc x] (assoc acc x true)) {} x))}]
    (is (= nil
           (json/parse-string
            (json/generate-string nil))))
    (is (= {"foo" {"bar" nil}}
           (json/parse-string
            (json/generate-string {"foo" {"bar" nil}})))))

  (testing "coercing nil"
    (binding [clj-json.core/*coercions* {nil (constantly "empty")}]
      (is (= "empty"
             (json/parse-string
              (json/generate-string nil))))
      (is (= {"foo" {"bar" "empty"}}
             (json/parse-string
              (json/generate-string {"foo" {"bar" nil}})))))))


(deftest test-bigdecimal
  (is (= "{\"data1\":1,\"data2\":1.11}"
   (json/generate-string {:data1 1M :data2 1.11M}))))

(deftest test-future
  (is (= "42"
         (json/generate-string (future 42)))))

(deftest test-atom
  (is (= "42"
         (json/generate-string (atom 42)))))
