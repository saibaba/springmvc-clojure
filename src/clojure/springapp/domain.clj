(ns springapp.domain
  (:require [springapp.bean :refer :all]))

(gen-class
  :name springapp.domain.Product
  :implements [java.io.Serializable]
  :main false
  :state state
  :init init
  :prefix "product-"
  :methods [[setDescription [String] void] [getDescription [] String]
           [setPrice [java.lang.Double] void] [getPrice [] java.lang.Double]])

(defn product-init
  []
  [ [] (atom {:description "" :price 0.0 })])

(defn product-setDescription
  [this description]
  (springapp.bean/set-field this :description description))

(defn product-getDescription
  [this]
  (springapp.bean/get-field this :description))

(defn product-setPrice
  [this price]
  (springapp.bean/set-field this :price price))

(defn product-getPrice
  [this]
  (springapp.bean/get-field this :price))

