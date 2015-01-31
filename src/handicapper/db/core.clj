(ns handicapper.db.core
  (:use korma.core
        [korma.db :only (defdb)])
  (:require [handicapper.db.schema :as schema]))

(defdb db schema/db-spec)

(defentity handicaps)

(defn save-handicap
  [name handicap]
  (insert handicaps
          (values {:name name
                   :handicap handicap
                   :timestamp (new java.util.Date)})))

(defn get-handicaps []
  (select handicaps))