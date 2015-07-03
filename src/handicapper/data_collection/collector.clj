(ns handicapper.data_collection.collector
  (:require [handicapper.db.core :as db]
            [handicapper.data-collection.parser :as parser])
  (:require [clj-http.client :as client]))

(defn store-in-db [pair]
  (let [name (:name pair)
        handicap (:handicap pair)]
    (db/save-handicap name handicap)))

(defn collect [id]
  (-> (client/get (str "http://www.runbritainrankings.com/runners/profile.aspx?athleteid=" id))
      :body
      parser/extract-handicap
      store-in-db))

(defn get-id [firstname surname]
    (-> (client/post "http://www.runbritainrankings.com/runners/profile.aspx"
                    (str "__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=%2FwEPDwULLTE5MjY0NTk0NDcPZBYCZg9kFgQCAw9kFgICAQ8WAh4FYWxpZ24FBmNlbnRlchYCAgEPFgIeBXdpZHRoBQU5NzVweBYCZg9kFgJmD2QWBAIBD2QWBgINDw8WAh4HVmlzaWJsZWgWAh4Hb25jbGljawU4cmV0dXJuIGZuQXJlWW91U3VyZSgnQXJlIHlvdSBzdXJlIHlvdSB3YW50IHRvIGxvZ291dCA%2FJylkAg8PDxYCHgRUZXh0BVZGb3Jnb3R0ZW4geW91ciBsb2dpbiBkZXRhaWxzPyA8YSBocmVmPSIvdXNlci91c2VycGFzc3dvcmRyZW1pbmRlci5hc3B4Ij5SZW1pbmQgTWU8L2E%2BLmRkAhEPFgIfBAUTQ2xhaW0geW91ciBIYW5kaWNhcGQCAw9kFgICCw8PFgIfAmhkZAIFD2QWAmYPFgIfBAWWAzxzY3JpcHQgdHlwZT0idGV4dC9qYXZhc2NyaXB0Ij4NdmFyIGdhSnNIb3N0ID0gKCgiaHR0cHM6IiA9PSBkb2N1bWVudC5sb2NhdGlvbi5wcm90b2NvbCkgPyAiaHR0cHM6Ly9zc2wuIiA6ICJodHRwOi8vd3d3LiIpOw1kb2N1bWVudC53cml0ZSh1bmVzY2FwZSgiJTNDc2NyaXB0IHNyYz0nIiArIGdhSnNIb3N0ICsgImdvb2dsZS1hbmFseXRpY3MuY29tL2dhLmpzJyB0eXBlPSd0ZXh0L2phdmFzY3JpcHQnJTNFJTNDL3NjcmlwdCUzRSIpKTsNPC9zY3JpcHQ%2BDTxzY3JpcHQgdHlwZT0idGV4dC9qYXZhc2NyaXB0Ij4NdHJ5IHsNdmFyIHBhZ2VUcmFja2VyID0gX2dhdC5fZ2V0VHJhY2tlcigiVUEtODIwNzkwMS03Iik7DXBhZ2VUcmFja2VyLl90cmFja1BhZ2V2aWV3KCk7DX0gY2F0Y2goZXJyKSB7fTwvc2NyaXB0Pg1kGAIFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYBBRpjdGwwMCRiYW5uZXIkY2hrUmVtZW1iZXJNZQUXY3RsMDAkY3BoQm9keSRndlJ1bm5lcnMPZ2RwewWdVIaufZZUSKq1FQeOU%2Ft2Kw%3D%3D&__EVENTVALIDATION=%2FwEWCQLJs8qqCALE1Kf0CwKZvrDeDQLNm7KvCgKkl8j3BQLyusu1AwKe8serBgLg5bjEBQL%2B8NQoZlm5oeHjg5PpIzgpN9PD%2B1HNxeA%3D&ctl00%24banner%24txtEmail=&ctl00%24banner%24txtPassword=&ctl00%24cphBody%24txtSurname="
                    surname
                    "&ctl00%24cphBody%24txtFirstName="
                    firstname
                    "&ctl00%24cphBody%24txtClub=&ctl00%24cphBody%24btnLookup=LookupName"))
         :body
         parser/extract-id))
