(ns springapp.web.inventory-controller-test
  (:import org.springframework.web.servlet.mvc.Controller)
  (:require [clojure.test :refer :all]
            [springapp.web.inventory :refer :all]))

(defn create-sample-inventory-controller
  []
  (let [ controller (springapp.web.InventoryController. )
         spm (springapp.service.SimpleProductManager. )
         x (.setProductDao spm (springapp.repository.InMemoryProductDao. (java.util.ArrayList. ))) ]
    (.setProductManager controller spm)
    controller))

(deftest test-inventory-clojure-controller
  (testing "Test Clojure InventoryController in Clojure"
    (let [ controller (create-sample-inventory-controller)
           model-and-view (.handleRequest controller nil nil)
           view-name (.getViewName model-and-view)
           modelObj (.getModel model-and-view)
           modelMap (.get modelObj "model")
           now-value (.get modelMap "now") ]
      (is (=  "helloclj" view-name))
      (is (not (nil? now-value))))))

(run-tests)
