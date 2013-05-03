(ns starbuck.csv-util
  (:require [clojure-csv.core :as csv]))

(defn open-csv 
  "Open a csv given a path"
  [csv-path]
    (csv/parse-csv (slurp csv-path)))

(defn csv-header-keys 
  "Get a vector of header keys from
   the first row of a csv file at csv-path. Assumes 
   first row of data contains header info"
  [csv-path] 
    (vec (map keyword 
      (first (open-csv csv-path)))))

(defn csv-data 
  "Get a vector of data rows from a csv file at csv-path.
   Assumes first row contains header information."
  [csv-path]
    (vec (rest (open-csv csv-path))))

(defn csv-to-rs-map 
  "Convert a csv file to the same format as a
   sql result set map"
  [csv-path]
  (map (fn [row] (zipmap (csv-header-keys csv-path) row)) 
        (csv-data csv-path)))


(defn seq-map-to-csv 
  "Take a sequence of maps and convert it into a csv file again
   This could be used in conjunction with spit like: 
   (spit 'your_csv_file.csv' (seq-map-to_csv args))"
  [seq-map]
  (str (csv/write-csv [(map name (keys (first seq-map)))])
          (csv/write-csv (for [m seq-map] (vec (map str (vals m))))
            )))