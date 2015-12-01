(ns springapp.web
  (:require [springapp.bean :refer :all])
  (:import 
           org.apache.commons.logging.LogFactory
           javax.servlet.http.HttpServletRequest
           javax.servlet.http.HttpServletResponse
           org.springframework.web.servlet.ModelAndView
           org.springframework.web.servlet.mvc.Controller))
(gen-class
  :name springapp.web.ClojureHWController
  :main false
  :init init
  :state state
  :prefix "hw-controller-"
  :implements [org.springframework.web.servlet.mvc.Controller])

(defn hw-controller-init
  []
  (let [logger (LogFactory/getLog springapp.web.ClojureHWController) ]
    [ [] (atom {:logger logger })]))

(defn hw-controller-handleRequest [this req resp]
  (let [now (.toString (java.util.Date. )) ]
    (.info (springapp.bean/get-field this :logger) (str "returning hello view with " now))
    (org.springframework.web.servlet.ModelAndView. "helloclj" "now" now)))
