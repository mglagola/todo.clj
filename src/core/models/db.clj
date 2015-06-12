(ns core.models.db
  (:require [clojure.java.jdbc :as sql]))

(def db-spec "postgresql://localhost:5432/core")

(defn table-created? [table-name]
  (-> (sql/query db-spec
                 [(str "select count(*) from information_schema.tables "
                       "where table_name='" (name table-name) "'")])
      first :count pos?))