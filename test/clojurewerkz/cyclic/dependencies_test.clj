(ns clojurewerkz.cyclic.dependencies-test
  (:require [clojurewerkz.cyclic.dependencies :as cy]
            [clojure.test :refer [deftest is are]]))


(deftest test-individual-node-detection-with-no-dependencies
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{}}
        xs [a b c]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a false
         b false
         c false)))

(deftest ^:focus test-individual-node-detection-with-mutual-dependency
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'a}}
        c {:name 'c :dependencies #{}}
        xs [a b c]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a true
         b true
         c false)))

(deftest test-individual-node-detection-with-circular-dependency1
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{'a}}
        xs [a b c]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a true
         b true
         c true)))

(deftest test-individual-node-detection-with-circular-dependency2
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{'d}}
        d {:name 'd :dependencies #{'e}}
        e {:name 'e :dependencies #{'f}}
        f {:name 'f :dependencies #{'a}}
        xs [a b c d e f]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a true
         b true
         c true
         d true
         e true
         f true)))

(deftest test-individual-node-detection-with-circular-dependency3
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{'d}}
        d {:name 'd :dependencies #{'e}}
        e {:name 'e :dependencies #{'b}}
        xs [a b c d e]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a true
         b true
         c true
         d true
         e true)))

(deftest test-individual-node-detection-with-circular-dependency4
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{'d}}
        d {:name 'd :dependencies #{'b}}
        e {:name 'e :dependencies #{}}
        xs [a b c d e]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a true
         b true
         c true
         d true
         e false)))

(deftest test-individual-node-detection-with-circular-dependency5
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{'a}}
        d {:name 'd :dependencies #{'b}}
        e {:name 'e :dependencies #{}}
        xs [a b c d e]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a true
         b true
         c true
         e false)))

(deftest test-individual-node-detection-with-circular-dependency6
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{'d 'a}}
        d {:name 'd :dependencies #{'e 'b}}
        e {:name 'e :dependencies #{'c 'b 'a}}
        xs [a b c d e]]
    (are [x res] (is (= res (cy/has-cyclic-dependencies? x xs)))
         a true
         c true
         d true
         e true)))


(deftest test-detection-with-no-dependencies
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{}}
        xs [a b c]]
    (is (nil? (cy/detect xs)))))

(deftest ^:focus test-individual-node-detection-with-mutual-dependency
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'a}}
        c {:name 'c :dependencies #{}}
        xs [a b c]]
    (is (cy/detect xs))))

(deftest test-individual-node-detection-with-circular-dependency1
  (let [a {:name 'a :dependencies #{'b}}
        b {:name 'b :dependencies #{'c}}
        c {:name 'c :dependencies #{'a}}
        xs [a b c]]
    (is (cy/detect xs))))
