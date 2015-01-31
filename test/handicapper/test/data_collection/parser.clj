(ns handicapper.test.data-collection.parser
  (:use clojure.test
        handicapper.data-collection.parser))

;;TODO Add tests for invalid html input

(deftest test-data-parsing
  (testing "parsing of raw html"
    (let [parsed (extract-handicap "<span id=\"ctl00_cphBody_lblAthleteName\">Mohamed Farah</span>\n<div id=\"h-number\">\n-6.7\n</div>")]
      (is (= {:name "Mohamed Farah" :handicap "-6.7"} parsed)))))
