(ns clojurewerkz.cyclic.dependencies
  "Generic cyclic dependency resolution functions"
  (:require [clojure.set :as set]))

;;
;; Implementation
;;

(defn ^:private cyclic-path
  [name dependencies coll]
  )

(defn tableize
  [coll]
  (reduce (fn [acc {:keys [name depends]}]
            (concat acc (for [x [name]
                              y depends]
                          [x y])))
          []
          coll))

(defn ^:private find-node
  [coll name]
  (some (fn [m]
          (when (= (:name m) name)
            m))
        coll))

(defn ^:private member?
  [m coll]
  (some #{m} coll))

(def ^:dynamic *resolved* (atom #{}))

(defn has-cyclic-dependencies?
  ([m coll]
     (has-cyclic-dependencies? m coll #{}))
  ([m coll seen]
     (println "Checking " m ", seen: " seen)
     (if (and (seq (:dependencies m))
              (not (= (count @*resolved*) (count coll)))
              (not (set/subset? (map :name (:dependencies m)) @*resolved*)))
       (do
         (loop [deps (:dependencies m)]
         (if-let [d (first deps)]
           (if (and (member? d seen)
                    (not (member? d @*resolved*)))
             (do
               (println "Already seen " d " which is not yet resolved!")
               true)
             (let []
               (println "Reached " d ", seen " seen " union: " #{(:name m) d} ", resolved: " @*resolved*)
               
               (if (has-cyclic-dependencies? (find-node coll d) coll (set/union #{(:name m) d} seen))
                 true
                 (recur (rest deps)))))
           false))
         (swap! *resolved* conj (:name m)))
       false)))


;;
;; API
;;

(defn detect
  [coll]
  (binding [*resolved* (atom #{})]
    (some (fn [m] (has-cyclic-dependencies? m coll #{}))
        coll)))
