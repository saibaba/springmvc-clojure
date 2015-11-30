(ns springapp.web.price
  (:require [springapp.bean :refer :all])
  (:import 
           springapp.service.PriceIncrease
           org.apache.commons.logging.LogFactory
           javax.servlet.http.HttpServletRequest
           javax.servlet.http.HttpServletResponse
           org.springframework.web.servlet.ModelAndView
           org.springframework.web.bind.annotation.RequestMapping
           org.springframework.web.bind.annotation.RequestMethod
           org.springframework.beans.factory.annotation.Autowired
           org.springframework.ui.ModelMap
           org.springframework.validation.BindingResult
           org.springframework.web.bind.annotation.InitBinder
           org.springframework.web.bind.WebDataBinder
           org.springframework.stereotype.Controller))

(gen-class
  :name ^{org.springframework.stereotype.Controller "" org.springframework.web.bind.annotation.RequestMapping {:value ["/clojure/priceincrease"]} } springapp.web.PriceIncreaseController
  :main false
  :init init
  :state state
  :prefix "price-increase-controller-"
  :methods [
    [ ^{org.springframework.web.bind.annotation.RequestMapping {:method [org.springframework.web.bind.annotation.RequestMethod/GET]}} initializeForm [org.springframework.ui.ModelMap] org.springframework.web.servlet.ModelAndView]
    [ ^{org.springframework.web.bind.annotation.RequestMapping {:method [org.springframework.web.bind.annotation.RequestMethod/POST]}} onSubmit [springapp.service.PriceIncrease org.springframework.validation.BindingResult] String]
    [^{org.springframework.beans.factory.annotation.Autowired {} } setProductManager [springapp.service.ProductManager] void]
    [^{org.springframework.beans.factory.annotation.Autowired {} } setValidator [springapp.service.PriceIncreaseValidator] void]
    [getProductManager [] springapp.service.ProductManager]])

(defn price-increase-controller-init
  []
  (let [logger (LogFactory/getLog springapp.web.PriceIncreaseController) ]
    [ [] (atom {:productManager nil :logger logger :validator nil })]))

(defn price-increase-controller-setProductManager
  [this productManager]
  (springapp.bean/set-field this :productManager productManager))

(defn price-increase-controller-setValidator
  [this validator]
  (springapp.bean/set-field this :validator validator))

(defn price-increase-controller-initializeForm
  [this model-map]
  (let [now (.toString (java.util.Date. )) priceIncrease (springapp.service.PriceIncrease. ) ]
    (.addAttribute model-map "priceIncrease", priceIncrease)
    (org.springframework.web.servlet.ModelAndView. "priceincreaseform" "now" now)))

(defn price-increase-controller-onSubmit
  [this price-increase binding-result]
  (let [now (.toString (java.util.Date. ))
        pm (springapp.bean/get-field this :productManager)
        percentage (.getPercentage price-increase)
        validator (springapp.bean/get-field this :validator) ]
    (.validate validator price-increase binding-result)
    (if (.hasErrors binding-result)
      "priceincreaseform"
      (do (.increasePrice pm percentage) "redirect:/"))))
