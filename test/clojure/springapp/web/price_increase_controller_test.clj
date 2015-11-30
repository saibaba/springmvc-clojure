(ns springapp.web.price-increase-controller-test
  (:import org.springframework.web.servlet.mvc.Controller
           org.springframework.ui.ModelMap)
  (:require [clojure.test :refer :all]
            [springapp.web.price :refer :all]))

(defn create-sample-price-increase-controller
  []
  (let [ controller (springapp.web.PriceIncreaseController. ) ]
    controller))

(deftest test-price-increase-clojure-controller
  (testing "Test Clojure PriceIncreaseController written in Clojure"
    (let [ controller (create-sample-price-increase-controller)
           model-map (org.springframework.ui.ModelMap. )
           model-and-view (.initializeForm controller model-map)
           view-name (.getViewName model-and-view)
           model (.getModel model-and-view)
           now-value (.get model "now") ]
      (is (=  "priceincreaseform" view-name))
      (is (not (nil? now-value))))))

(run-tests)
