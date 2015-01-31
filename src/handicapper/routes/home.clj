(ns handicapper.routes.home
  (:require [handicapper.layout :as layout]
            [handicapper.db.core :as db])
  (:require [compojure.core :refer :all]))

(defn home-page []
  (layout/render "home.html"
                 {:handicaps (db/get-handicaps)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))
