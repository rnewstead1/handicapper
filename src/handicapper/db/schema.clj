(ns handicapper.db.schema
  (:require [clojure.java.jdbc :as sql]
            [clojure.java.io :refer [file]]))

(def db-store (str (.getName (file ".")) "/site.db"))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2"
              :subname db-store
              :user "sa"
              :password ""
              :make-pool? true
              :naming {:keys clojure.string/lower-case
                       :fields clojure.string/upper-case}})
(defn initialized?
  "checks to see if the database schema is present"
  []
  (.exists (file (str db-store ".mv.db"))))

(defn- drop-handicaps-table []
  (sql/db-do-commands
    db-spec
    (sql/drop-table-ddl :handicaps)))

(defn- create-handicaps-table []
  (sql/db-do-commands
    db-spec
    (sql/create-table-ddl
      :handicaps
      [:id "INTEGER PRIMARY KEY AUTO_INCREMENT"]
      [:timestamp :timestamp]
      [:name "varchar(30)"]
      [:handicap "varchar(30)"]))
  (sql/db-do-prepared db-spec
                      "CREATE INDEX timestamp_index ON handicaps (timestamp)"))

(defn create-tables
  "creates the database tables used by the application"
  []
  (create-handicaps-table))

(defn empty-data
  "wipes database"
  []
  (drop-handicaps-table)
  (create-handicaps-table))
