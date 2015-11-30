(ns springapp.web.error
  (:require [springapp.bean :refer :all])
  (:import
           org.apache.commons.logging.LogFactory
           javax.servlet.http.HttpServletRequest
           javax.servlet.http.HttpServletResponse
org.springframework.web.servlet.HandlerExceptionResolver
org.springframework.core.Ordered
           org.springframework.web.servlet.ModelAndView))

(gen-class
  :name springapp.web.LoggingHandlerExceptionResolver
  :main false
  :init init
  :state state
  :prefix "logging-handler-exception-resolver-"
  :implements [org.springframework.web.servlet.HandlerExceptionResolver, org.springframework.core.Ordered])

(defn logging-handler-exception-resolver-init
  []
  (let [logger (LogFactory/getLog springapp.web.LoggingHandlerExceptionResolver) ]
    [ [] (atom {:logger logger })]))

(defn logging-handler-exception-resolver-getOrder
  [this]
  (org.springframework.core.Ordered/HIGHEST_PRECEDENCE))

(defn logging-handler-exception-resolver-resolveException
  [this req resp handler exc]
  (let [now (.toString (java.util.Date. ))]
    (.error (springapp.bean/get-field this :logger) (str "Exception at " now))
    (.error (springapp.bean/get-field this :logger) exc)
    (.printStackTrace exc)
    nil))
