(ns clojurewerkz.cyclic.dependencies
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
