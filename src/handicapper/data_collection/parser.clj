(ns handicapper.data-collection.parser)

(defn extract-handicap [raw-html]
  (let [name (get (re-find #"id=.*AthleteName\">(.*)<" raw-html) 1)
        handicap (get (re-find #"h-number.*\n*\s*(.*)" raw-html) 1)]
    {:name name :handicap handicap}))

(defn extract-id [raw-html]
    (get (re-find #"id=\/runners/profile.aspx?athleteid=(.*)" raw-html) 1))