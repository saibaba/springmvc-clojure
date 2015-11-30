(ns springapp.web.inventory
  (:require [springapp.bean :refer :all])
  (:import
           springapp.service.ProductManager
           org.apache.commons.logging.LogFactory
           javax.servlet.http.HttpServletRequest
           javax.servlet.http.HttpServletResponse
           org.springframework.web.servlet.ModelAndView
           org.springframework.web.servlet.mvc.Controller))

(gen-class
  :name springapp.web.InventoryController
  :main false
  :init init
  :state state
  :prefix "inventory-controller-"
  :methods [[setProductManager [springapp.service.ProductManager] void] [getProductManager [] springapp.service.ProductManager]]
  :implements [org.springframework.web.servlet.mvc.Controller])

(defn inventory-controller-init
  []
  (let [logger (LogFactory/getLog springapp.web.InventoryController) ]
    [ [] (atom {:productManager nil :logger logger })]))

(defn inventory-controller-handleRequest
  [this req resp]
  (let [now (.toString (java.util.Date. )) my-model (java.util.HashMap. )]
    (.put my-model "products" (.getProducts (springapp.bean/get-field this :productManager)))
    (.put my-model "now" now)
    (org.springframework.web.servlet.ModelAndView. "helloclj" "model" my-model)))

(defn inventory-controller-setProductManager
  [this productManager]
  (springapp.bean/set-field this :productManager productManager))

