(ns springapp.service
  (import org.springframework.validation.Validator)
  (:require [springapp.bean :refer :all]))

;;;; ProductManager = a service as in service layer pattern
(gen-interface
  :name    springapp.service.ProductManager
  :extends [java.io.Serializable]
  :methods [ [getProducts [] java.util.List]
           [increasePrice [int] void] ])

;;;; An implementation of ProductManager

(gen-class
  :name   springapp.service.SimpleProductManager
  :implements [springapp.service.ProductManager]
  :main   false
  :state  state
  :init   init
  :methods [ [^{org.springframework.beans.factory.annotation.Autowired {} } setProductDao [springapp.repository.ProductDao] void] ]
  :prefix "simple-product-manager-")

(defn simple-product-manager-init
  []
  [ [] (atom {:productDao nil })])

(defn simple-product-manager-getProducts
  [this]
  (.getProductList (springapp.bean/get-field this :productDao)))

(defn simple-product-manager-setProductDao
  [this productDao]
  (springapp.bean/set-field this :productDao productDao))

(defn calculate-new-price
  [price percent]
  (* price (/ (+ 100.0 percent) 100.0)))

(defn increase-product-price
  [this product percent]
  (let [new-price (calculate-new-price (.getPrice product) percent) productDao (springapp.bean/get-field this :productDao) ]
    (.setPrice product new-price)
    (.saveProduct productDao product)
    product))

(defn simple-product-manager-increasePrice
  [this percent]
  (let [products (.getProductList (springapp.bean/get-field this :productDao))]
    ; calling doall to realize the lazy sequence created by map
    (doall (map #(increase-product-price this %1 percent) products))))

;;;; Value object used between client (controller) and its view (jsp) - view model

(gen-class
  :name   springapp.service.PriceIncrease
  :main   false
  :state  state
  :init   init
  :methods [[setPercentage [int] void] [getPercentage [] int]]
  :prefix "price-increase-")

(defn price-increase-init
  []
  [ [] (atom {:percentage (int 0) })])

(defn price-increase-setPercentage
  [this percentage]
  (springapp.bean/set-field this :percentage percentage))

(defn price-increase-getPercentage
  [this]
  (springapp.bean/get-field this :percentage))

;;;; A validator for the content in value object (submitted by untrusted sources like web form)
(gen-class
  :name   springapp.service.PriceIncreaseValidator
  :main   false
  :state  state
  :init   init
  :implements [org.springframework.validation.Validator]
  :methods [[setMinPercentage [int] void] [getMinPercentage [] int]
            [setMaxPercentage [int] void] [getMaxPercentage [] int]]
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
