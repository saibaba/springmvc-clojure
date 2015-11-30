(ns springapp.web-test
  (:import org.springframework.web.servlet.mvc.Controller)
  (:require [clojure.test :refer :all]
            [springapp.web :refer :all]))

(deftest test-hello-world-clojure-controller
  (testing "Test Clojure HelloWorld Controller"
    (let [ controller (springapp.web.ClojureHWController. )
           model-and-view (.handleRequest controller nil nil)
           view-name (.getViewName model-and-view)
           model (.getModel model-and-view)
           now-value (.get model "now") ]
      (is (=  "helloclj" view-name))
      (is (not (nil? now-value))))))

(run-tests)
