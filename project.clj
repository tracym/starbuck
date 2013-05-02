(defproject starbuck "0.1.0-SNAPSHOT"
  :description "library of functions for manipulating tabular data from various sources in clojure"
  :url "https://github.com/tracym/starbuck"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
  				 [org.clojure/java.jdbc "0.3.0-alpha1"]
  				 [postgresql/postgresql "8.4-702.jdbc4"]
		 		 [clojure-csv/clojure-csv "2.0.0-alpha1"]
		 		 [net.sourceforge.jtds/jtds "1.2.4"]])
