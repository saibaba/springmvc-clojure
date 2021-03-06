(ns springapp.util
  (:require [springapp.bean :refer :all]))

(defmacro info [message]
  `(.info (springapp.bean/get-field ~(symbol (str "this")) :logger) ~message))
 
(defmacro error [message]
  `(.error (springapp.bean/get-field ~(symbol (str "this")) :logger) ~message))
