(ns springapp.repository
  (:import
    javax.sql.DataSource
    org.springframework.jdbc.core.RowMapper
    org.springframework.jdbc.core.JdbcTemplate
    org.springframework.jdbc.core.simple.SimpleJdbcInsert
    org.apache.commons.logging.LogFactory)
  (:require [springapp.bean :refer :all]))

(gen-interface
  :name    springapp.repository.ProductDao
  :methods [[getProductList [] java.util.List]
           [deleteProducts [] void]
           [saveProduct [springapp.domain.Product] void] ])

(gen-class
  :name   springapp.repository.ProductMapper
  :implements [org.springframework.jdbc.core.RowMapper]
  :methods [[mapRow [java.sql.ResultSet java.lang.Integer] springapp.domain.Product ]]
  :main   false
  :prefix "product-mapper-")

(defn product-mapper-mapRow
  [this rs rowNum]
  (let [product (springapp.domain.Product. )]
    (.setId product (.getInt rs "id"))
    (.setDescription product (.getString rs "description"))
    (.setPrice product (.getDouble rs "price"))
    product))

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
  (let [logger (springapp.bean/get-field this :logger) jdbcTemplate (springapp.bean/get-field this :jdbcTemplate)]
    (.info logger (str "Getting products!"))
    (.query jdbcTemplate "select id, description, price from products" (springapp.repository.ProductMapper. )) ))

(defn jdbc-product-dao-saveProduct
  [this product]
  (let [logger (springapp.bean/get-field this :logger) jdbcTemplate (springapp.bean/get-field this :jdbcTemplate) ]
    (.info logger (str "updating product with description " (.getDescription product) ))
    (let [ ps (java.util.HashMap. )
         x  (do
              (.put ps "description" (.getDescription product) )
              (.put ps "price" (.getPrice product))
              (.put ps "id" (.getId product)))
         count (.update jdbcTemplate "update products set description = ?, price = ? where id = ?" (to-array [(.getDescription product) (.getPrice product) (.getId product)]) )]
      (.info logger (str "\t rows affected" count ))
      count)))

(defn jdbc-product-dao-deleteProducts
  [this]
  (let [logger (springapp.bean/get-field this :logger) jdbcTemplate (springapp.bean/get-field this :jdbcTemplate)]
    (.info logger (str "deleting all products"))
    (.update jdbcTemplate "delete from products")))
