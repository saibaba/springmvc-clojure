(ns springapp.web.shell
  (:require [springapp.bean :refer :all])
  (:import 
           org.apache.commons.logging.LogFactory
           javax.servlet.http.HttpServletRequest
           javax.servlet.http.HttpServletResponse
           org.springframework.web.servlet.ModelAndView
           org.springframework.web.bind.annotation.RequestMapping
           org.springframework.web.bind.annotation.RequestMethod
           org.springframework.beans.factory.annotation.Autowired
           org.springframework.ui.ModelMap
           org.springframework.stereotype.Controller))

;;;; Value object used between client (controller) and its view (jsp) - view model

(gen-class
  :name   springapp.web.Script
  :main   false
  :state  state
  :init   init
  :methods [
             [setSource [String] void] [getSource [] String]
             [setResult [String] void] [getResult [] String]
           ]
  :prefix "script-")

(defn script-init
  []
  [ [] (atom {:source "((fn [] \"Hello Clojure World!\"))" :result "" })])

(defn script-setSource
  [this source]
  (springapp.bean/set-field this :source source))

(defn script-getSource
  [this]
  (springapp.bean/get-field this :source))

(defn script-setResult
  [this result]
  (springapp.bean/set-field this :result result))

(defn script-getResult
  [this]
  (springapp.bean/get-field this :result))

(gen-class
  :name ^{org.springframework.stereotype.Controller "" org.springframework.web.bind.annotation.RequestMapping {:value ["/clojure/shell"]} } springapp.web.ClojureShellController
  :implements [org.springframework.context.ApplicationContextAware]
  :main false
  :init init
  :state state
  :prefix "clojure-shell-controller-"
  :methods [
    [ ^{org.springframework.web.bind.annotation.RequestMapping {:method [org.springframework.web.bind.annotation.RequestMethod/GET]}}
       initializeForm [org.springframework.ui.ModelMap] org.springframework.web.servlet.ModelAndView]
    [ ^{org.springframework.web.bind.annotation.RequestMapping {:method [org.springframework.web.bind.annotation.RequestMethod/POST]}}
       onSubmit [springapp.web.Script org.springframework.validation.BindingResult] org.springframework.web.servlet.ModelAndView] ])

(defn clojure-shell-controller-init
  []
  (let [logger (LogFactory/getLog springapp.web.ClojureShellController) ]
    [ [] (atom {:applicationContext nil :logger logger})]))

(defn clojure-shell-controller-setApplicationContext
  [this ctx]
  (springapp.bean/set-field this :applicationContext ctx))

(defn clojure-shell-controller-initializeForm
  [this model-map]
  (let [now (.toString (java.util.Date. )) script (springapp.web.Script. ) ]
    (.addAttribute model-map "shell", script)
    (org.springframework.web.servlet.ModelAndView. "shellform" "now" now)))

(defn eval-string
  [s]
  (str (eval (read-string s))))

(defn clojure-shell-controller-onSubmit
  [this script binding-result]
  (let [now (.toString (java.util.Date. )) result (eval-string (.getSource script)) mav (org.springframework.web.servlet.ModelAndView. )]
     (.setResult script result)
     (.setViewName mav "shellform")
     (.addObject mav "shell" script)
     mav))
