(ns springapp.web.interceptors
  (:require [springapp.bean :refer :all])
  (:import 
           org.springframework.web.servlet.ModelAndViewDefiningException 
           org.springframework.web.servlet.handler.HandlerInterceptorAdapter
           java.util.Calendar
           org.apache.commons.logging.LogFactory
           javax.servlet.http.HttpServletRequest
           javax.servlet.http.HttpServletResponse
           org.springframework.web.servlet.ModelAndView
           org.springframework.web.bind.annotation.RequestMapping
           org.springframework.web.bind.annotation.RequestMethod
           org.springframework.beans.factory.annotation.Autowired))

(gen-class
  :name springapp.web.TimeBasedAccessInterceptor
  :extends org.springframework.web.servlet.handler.HandlerInterceptorAdapter
  :main false
  :init init
  :state state
  :prefix "time-based-access-interceptor-"
  :methods [
    [ setOpeningTime [int] void]
    [ getOpeningTime [] int]
    [ setClosingTime [int] void]
    [ getClosingTime [] int] ])

(defn time-based-access-interceptor-init
  []
  (let [logger (LogFactory/getLog springapp.web.TimeBasedAccessInterceptor) ]
    [ [] (atom {:openingTime 0 :closingTime 0 :logger logger })]))

(defn time-based-access-interceptor-setOpeningTime
  [this openingTime]
  (springapp.bean/set-field this :openingTime openingTime))

(defn time-based-access-interceptor-getOpeningTime
  [this]
  (springapp.bean/get-field this :openingTime))

(defn time-based-access-interceptor-setClosingTime
  [this closingTime]
  (springapp.bean/set-field this :closingTime closingTime))

(defn time-based-access-interceptor-getClosingTime
  [this]
  (springapp.bean/get-field this :closingTime))

(defn time-based-access-interceptor-preHandle
  [this request response handler]
  (let [ cal (Calendar/getInstance) hour (.get cal (Calendar/HOUR_OF_DAY)) openingTime (.getOpeningTime this) closingTime (.getClosingTime this) ]
    (if (and (<= openingTime hour) (< hour closingTime))
      true
      (let [mav (ModelAndView. "outsideOfficeHours") ]
        (throw (ModelAndViewDefiningException. mav))))))
