(ns hw.web
  (:import 
           javax.servlet.http.HttpServletRequest
           javax.servlet.http.HttpServletResponse
           org.springframework.web.servlet.ModelAndView
           org.springframework.web.servlet.mvc.Controller)

  (:gen-class
    :name hw.web.ClojureHWController
    :implements [org.springframework.web.servlet.mvc.Controller]
))

;    :methods [[ handleRequest [javax.servlet.http.HttpServletRequest javax.servlet.http.HttpServletResponse ] org.springframework.web.servlet.ModelAndView ]]
(defn -handleRequest [this req resp]
  (org.springframework.web.servlet.ModelAndView. "helloclj"))
