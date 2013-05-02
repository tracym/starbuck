(ns starbuck.sql-util
  (:require [clojure.java.jdbc :as sql]))

(defn exec-sql 
  "Given a connection, a sql string, binding parameters,
  execute a sql statement."
  [conn sql-stmt & bind-params]
          (sql/with-connection conn
          (sql/with-query-results rs 
            (vec (cons sql-stmt bind-params))
          (doall rs))))
