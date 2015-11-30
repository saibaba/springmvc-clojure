(ns springapp.service-test
  (:require [clojure.test :refer :all]
            [springapp.domain :refer :all]
            ;[clojure.test.junit :refer :all]
            [springapp.service :refer :all]))

(defn create-product-manager-for-tests
  []
  (let [ products (java.util.ArrayList. ) spm (springapp.service.SimpleProductManager. )]
    (let [p1 (springapp.domain.Product. )]
      (.setDescription p1 "Chair")
      (.setPrice p1 20.50)
      (.add products p1))
    (let [p2 (springapp.domain.Product. )]
      (.setDescription p2 "Table")
      (.setPrice p2 150.10)
      (.add products p2))
    (.setProductDao spm (springapp.repository.InMemoryProductDao. products))
    spm))
    
(deftest test-simple-product-manager
  (let [spm (create-product-manager-for-tests) ]
    (testing "Test Simple Product Manager with sample products"
      (is (= 2 (.size (.getProducts spm)))))))

(deftest test-simple-product-manager-no-products
  (let [spm (springapp.service.SimpleProductManager. ) x (.setProductDao spm (springapp.repository.InMemoryProductDao. nil)) ]
    (testing "Test Simple Product Manager with no products"
      (is (nil? (.getProducts spm))))))

(deftest test-simple-product-manager-increase-price-with-positive-percentage
  (let [spm (create-product-manager-for-tests) ]
    (testing "Test Simple Product Manager increasing price on sample products with + value"
      (.increasePrice spm (Integer. 10))
      (let [products (.getProducts spm) chair (.get products 0) table (.get products 1)]
      (is (= 22.55 (.getPrice chair)))
      (is (= 165.11 (.getPrice table)))))))

;(clojure.test.junit/with-junit-output (run-tests))
(run-tests)
