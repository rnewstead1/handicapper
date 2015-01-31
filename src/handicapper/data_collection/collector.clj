(ns handicapper.data_collection.collector
  (:require [handicapper.db.core :as db]
            [handicapper.data-collection.parser :as parser])
  (:require [clj-http.client :as client]))

(defn store-in-db [pair]
  (let [name (:name pair)
        handicap (:handicap pair)]
    (db/save-handicap name handicap)))

(defn collect []
  (-> (client/get "http://www.runbritainrankings.com/runners/profile.aspx?athleteid=527523")
      :body
      parser/extract-handicap
      store-in-db))
