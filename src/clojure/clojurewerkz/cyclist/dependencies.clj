;; Copyright (c) 2013-2014 Michael S. Klishin, Alex Petrov, and the ClojureWerkz Team
;;
;; The use and distribution terms for this software are covered by the
;; Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.html at the root of this distribution.
;; By using this software in any fashion, you are agreeing to be bound by
;; the terms of this license.
;; You must not remove this notice, or any other, from this software.

(ns clojurewerkz.cyclist.dependencies
  "Generic cyclic dependency resolution functions"
  (:require [clojure.set :as set]))

;;
;; Implementation
;;

(defn ^:private find-node
  [coll name]
  (some (fn [m]
          (when (= (:name m) name)
            m))
        coll))

(defn ^:private member?
  [m coll]
  (some #{m} coll))

(defn has-cyclic-dependencies?
  ([m coll]
     (has-cyclic-dependencies? m coll #{} (atom #{})))
  ([m coll seen resolved]
     (if (and (seq (:dependencies m))
              (not (set/subset? (map :name (:dependencies m)) @resolved)))
       (loop [deps (:dependencies m)]
         (if-let [d (first deps)]
           (if (and (member? d seen)
                    (not (member? d @resolved)))
             true
             (if (has-cyclic-dependencies? (find-node coll d) coll (set/union #{(:name m) d} seen) resolved)
               true
               (recur (rest deps))))
           (do
             (swap! resolved conj (:name m))
             false)))
       false)))


;;
;; API
;;

(defn detect
  [coll]
  (let [resolved (atom #{})]
    (some (fn [m] (has-cyclic-dependencies? m coll #{} resolved))
          coll)))
