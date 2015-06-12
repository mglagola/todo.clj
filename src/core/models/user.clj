(ns core.models.user
  (:require [clojure.java.jdbc :as sql]
            [core.models.db :as db]
            [noir.util.crypt :as crypt])
  (:import java.sql.DriverManager))

;; `user` is a keyword in postgresql, thus
;; we are using _user

(defn create-user-table-if-needed
  "Creates the user table"
  []
  (when (not (db/table-created? :_user))
    (sql/db-do-commands
      db/db-spec
      (sql/create-table-ddl
        :_user
        [:id :serial "PRIMARY KEY"]
        [:email :varchar "UNIQUE NOT NULL"]
        [:password :varchar]
        [:first_name :varchar]
        [:last_name :varchar]
        [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))))


(defn get-user-by-id
  "Get's a user by their id from the database"
  [id]
  (sql/query
    db/db-spec
    ["select * from _user where id = ?" id]))

(defn get-user-by-email
  "Get's a user by their email from the database"
  [email]
  (sql/query
    db/db-spec
    ["select * from _user where email = ?" email]))

(defn create-user
  "Creates a user via a map with the appropriate data corresponding
  to each key needed in the database"
  [user-spec]
  (sql/insert!
    db/db-spec
    :_user
    user-spec))

(defn create-user-and-encrypt-pass
  "Creates a user and also encrypts password"
  [{pass :password :as user-spec}]
  (create-user
    (assoc user-spec :password (crypt/encrypt pass))))
