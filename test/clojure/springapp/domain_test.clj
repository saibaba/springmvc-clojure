(ns springapp.domain-test
  (:require [clojure.test :refer :all]
            [springapp.domain :refer :all]))

(deftest test-product
  (let [p (springapp.domain.Product. ) ]
    (testing "Test Product domain object description field"
      (let [ testDescription "aDescription" ]
        (is (= "" (.getDescription p)))
        (.setDescription p testDescription)
        (is (= testDescription (.getDescription p)))))

    (testing "Test Product domain object price field"
      (let [ testPrice 100.0 ]
        (is (= 0.0 (.getPrice p)))
        (.setPrice p testPrice)
        (is (= testPrice (.getPrice p)))))

  ))

(run-tests)
