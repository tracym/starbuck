(ns starbuck.core
	(:use [clojure.set]))

(defn empty-unioned-map 
  "Create a map of keys from lmap and rmap - all mapped to nil"
  [lmap rmap]
  (let [ks (seq (union (set (keys lmap)) (set (keys rmap))))
        n (count ks)] 
      (zipmap ks (repeat n nil))))


(defn merge-with-empty 
  "merge data from lmap and rmap with nils where no data exists"
  [lmap rmap]
  (merge-with #(or %1 %2) lmap
    (empty-unioned-map lmap rmap)))

(defn map-difference 
  "todo: find out who I stole this from on stack overflow"
  [m1 m2]
  (let [ks1 (set (keys m1))
        ks2 (set (keys m2))
        ks1-ks2 (difference ks1 ks2)
        ks2-ks1 (difference ks2 ks1)
        ks1*ks2 (intersection ks1 ks2)]
    (merge (select-keys m1 ks1-ks2)
           (select-keys m2 ks2-ks1)
           (select-keys m1
                        (remove (fn [k] (= (m1 k) (m2 k)))
                                ks1*ks2)))))


(defn left-join-maps 
  "Lamely simulate a sql left join on lmap and rmap using keycol to 
   match data from either side"
  [lmap rmap keycol]
  (pmap 
    (fn [m] (let [res (filter #(= (% keycol) (m keycol)) rmap)]
             (if (empty? res)
              (merge-with-empty m (first rmap))
              (map-difference m (first res))))) lmap))

(defn create-unique-id
	"Add a unique identifier with the name keycol
	 to a seq of maps. The id begins with seed."
	[seq-map keycol seed]
	(map #(merge %1 {keycol %2}) seq-map (iterate inc seed)))

(defn diff-by-key 
	"Return the differences between two sequences of maps that share
	 a key column"
	[lmap rmap keycol]
	(difference (project (set lmap) [keycol]) (project (set rmap) [keycol])))


(defn filter-seq-by-map 
	"Filter a sequence of maps by a column and value (contained in a map)"
	 [seq-map keyval-map]
	(filter #(= (% (key keyval-map)) (val keyval-map)) seq-map))

(defn filter-seq-by-multiple
	"Filter a sequence of maps by a map of criteria"
	[seq-map filter-criteria]
	(reduce filter-seq-by-map seq-map filter-criteria))