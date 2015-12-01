(ns springapp.repository
  (:import
    javax.sql.DataSource
    org.springframework.jdbc.core.RowMapper
    org.springframework.jdbc.core.JdbcTemplate
    org.springframework.jdbc.core.simple.SimpleJdbcInsert
    org.apache.commons.logging.LogFactory)
  (:require [springapp.bean :refer :all]
            [springapp.util :refer :all]))

;;;; DAO interface
(gen-interface
  :name    springapp.repository.ProductDao
  :methods [[getProductList [] java.util.List]
           [deleteProducts [] void]
           [saveProduct [springapp.domain.Product] void] ])

;;;; Utility classes
(gen-class
  :name   springapp.repository.ProductMapper
  :implements [org.springframework.jdbc.core.RowMapper]
  :methods [[mapRow [java.sql.ResultSet int] springapp.domain.Product ]]
  :main   false
  :prefix "product-mapper-")

(defn product-mapper-mapRow
  [this rs rowNum]
  (doto (new springapp.domain.Product)
        (.setId (.getInt rs "id"))
        (.setDescription (.getString rs "description"))
        (.setPrice (.getDouble rs "price"))))

;;;; DAO implementation for a JDBC data source

(gen-class
  :name   springapp.repository.JdbcProductDao
  :implements [springapp.repository.ProductDao]
  :main   false
  :state  state
  :init   init
  :methods [[^{org.springframework.beans.factory.annotation.Autowired {} }  setDataSource [javax.sql.DataSource] void] ]
  :prefix "jdbc-product-dao-")

(defn jdbc-product-dao-init
  []
  (let [logger (LogFactory/getLog springapp.repository.JdbcProductDao) ]
    [ [] (atom {:logger logger :jdbcTemplate nil})]))

(defn jdbc-product-dao-setDataSource
  [this dataSource]
  (let [jdbcTemplate (org.springframework.jdbc.core.JdbcTemplate. dataSource)]
    (springapp.bean/set-field this :jdbcTemplate jdbcTemplate)))

(defn jdbc-product-dao-getProductList
  [this]
  (let [jdbcTemplate (springapp.bean/get-field this :jdbcTemplate)]
    (println (macroexpand-1 '(springapp.util/info "Getting products!")))
    (.query jdbcTemplate "select id, description, price from products" (springapp.repository.ProductMapper. )) ))

(defn jdbc-product-dao-saveProduct
  [this product]
  (let [jdbcTemplate (springapp.bean/get-field this :jdbcTemplate) ]
    (springapp.util/info (str "Updating product with description " (.getDescription product )))
    (let [ ps (java.util.HashMap. {"description" (.getDescription product) 
                                   "price"       (.getPrice product)
                                   "id"          (.getId product)})
           sql "update products set description = ?, price = ? where id = ?"
           params (to-array [(.getDescription product) (.getPrice product) (.getId product)])
           count (.update jdbcTemplate sql params)]
      (springapp.util/info (str "\t rows affected " count ))
      count)))

(defn jdbc-product-dao-deleteProducts
  [this]
  (let [jdbcTemplate (springapp.bean/get-field this :jdbcTemplate)]
    (springapp.util/info "deleting all products")
    (.update jdbcTemplate "delete from products")))
