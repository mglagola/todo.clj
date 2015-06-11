(ns core.models.todo
  (:require [clojure.java.jdbc :as sql]
            [core.models.db :as db]
            [noir.util.crypt :as crypt]))

(defn create-todo-table
  "Creates the todo table"
  []
  (sql/db-do-commands
    db/db-spec
    (sql/create-table-ddl
      :todo
      [:id :serial "PRIMARY KEY"]
      [:title :varchar "NOT NULL"]
      [:description :varchar]
      [:user_id :serial "REFERENCES _user (id)"]
      [:last_modified :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]
      [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn get-todo-by-id
  "Get's a user by their id from the database"
  [id]
  (sql/query
    db/db-spec
    ["select * from todo where id = ?" id]))

(defn create-todo
  "Creates a user via a map with the appropriate data corresponding
  to each key needed in the database"
  [todo-spec]
  (sql/insert!
    db/db-spec
    :todo
    todo-spec))