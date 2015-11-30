(ns springapp.service
  (import org.springframework.validation.Validator)
  (:require [springapp.bean :refer :all]))

(gen-interface
  :name    springapp.service.ProductManager
  :extends [java.io.Serializable]
  :methods [[setProducts [java.util.List] void] [getProducts [] java.util.List]
           [increasePrice [java.lang.Integer] void] ])

(gen-class
  :name   springapp.service.SimpleProductManager
  :implements [springapp.service.ProductManager]
  :main   false
  :state  state
  :init   init
  :prefix "simple-product-manager-")

(defn simple-product-manager-init
  []
  [ [] (atom {:products nil })])

(defn simple-product-manager-getProducts
  [this]
  (springapp.bean/get-field this :products))

(defn simple-product-manager-setProducts
  [this products]
  (springapp.bean/set-field this :products products))

(defn calculate-new-price
  [price percent]
  (* price (/ (+ 100.0 percent) 100.0)))

(defn increase-product-price
  [product percent]
  (let [new-price (calculate-new-price (.getPrice product) percent)]
    (.setPrice product new-price)
    product))

(defn simple-product-manager-increasePrice
  [this percent]
  (let [products (springapp.bean/get-field this :products)]
    ; calling doall to realize the lazy sequence created by map
    (doall (map (fn [p] (increase-product-price p percent)) products))))

(gen-class
  :name   springapp.service.PriceIncrease
  :main   false
  :state  state
  :init   init
  :methods [[setPercentage [java.lang.Integer] void] [getPercentage [] java.lang.Integer]]
  :prefix "price-increase-")

(defn price-increase-init
  []
  [ [] (atom {:percentage (java.lang.Integer. 0) })])

(defn price-increase-setPercentage
  [this percentage]
  (springapp.bean/set-field this :percentage percentage))

(defn price-increase-getPercentage
  [this]
  (springapp.bean/get-field this :percentage))

(gen-class
  :name   springapp.service.PriceIncreaseValidator
  :main   false
  :state  state
  :init   init
  :implements [org.springframework.validation.Validator]
  :methods [[setMinPercentage [java.lang.Integer] void] [getMinPercentage [] java.lang.Integer]
            [setMaxPercentage [java.lang.Integer] void] [getMaxPercentage [] java.lang.Integer]]
  :prefix "price-increase-validator-")

(defn price-increase-validator-init
  []
  [ [] (atom {:minPercentage 0 :maxPercentage 0 })])

(defn price-increase-validator-validate
  [this ^springapp.service.PriceIncrease priceIncrease errors]
    (let [ pi (.getPercentage priceIncrease) maxp (.getMaxPercentage this) minp (.getMinPercentage this) ]
      (if (> pi maxp)
        (.rejectValue errors "percentage" "error.too-high" (to-array [ maxp ])  "Value too high.")
        (if (<= pi minp)
          (.rejectValue errors "percentage" "error.too-low" (to-array [ minp ])  "Value too low.")
          nil))))

(defn price-increase-validator-setMinPercentage
  [this percentage]
  (springapp.bean/set-field this :minPercentage percentage))

(defn price-increase-validator-getMinPercentage
  [this]
  (springapp.bean/get-field this :minPercentage))

(defn price-increase-validator-setMaxPercentage
  [this percentage]
  (springapp.bean/set-field this :maxPercentage percentage))

(defn price-increase-validator-getMaxPercentage
  [this]
  (springapp.bean/get-field this :maxPercentage))
