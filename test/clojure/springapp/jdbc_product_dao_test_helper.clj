(ns springapp.jdbc-product-dao-test-helper
  (:import org.junit.Assert
           org.junit.Test
           org.springframework.test.context.ContextConfiguration
           org.springframework.test.context.transaction.TransactionConfiguration
           org.springframework.transaction.annotation.Transactional
           org.springframework.beans.factory.annotation.Autowired
           org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests)
  (:require [clojure.test :refer :all]
            [springapp.repository :refer :all]))

(gen-class
  :name ^{org.junit.runner.RunWith {:value org.springframework.test.context.junit4.SpringJUnit4ClassRunner } org.springframework.test.context.ContextConfiguration { :locations [ "classpath:test-context.xml" ]} } springapp.repository.JdbcProductDaoTests
  :extends org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests
  :main false
  :state state
  :init init
  :prefix "jdbc-product-dao-tests-"
  :methods [
            [^{org.springframework.beans.factory.annotation.Autowired {} }  setProductDao [springapp.repository.ProductDao] void]
            [^{org.springframework.beans.factory.annotation.Autowired {} }  setDataSourceChild [javax.sql.DataSource] void]
            [^{org.springframework.test.context.transaction.BeforeTransaction {} }  beforeTransaction [] void]
            [ ^{org.junit.Test {} } testGetProductList [] void] [^{org.junit.Test {} } testSaveProduct [] void]])

(defn jdbc-product-dao-tests-init
  []
  [ [] (atom {:productDao nil})])

(defn jdbc-product-dao-tests-setProductDao
  [this productDao]
  (springapp.bean/set-field this :productDao productDao))

; Do not know why the Autowired setDataSource is not working, probably has to do with gen-class
(defn jdbc-product-dao-tests-setDataSourceChild
  [this dataSource]
  (.setDataSource this dataSource))

(defn jdbc-product-dao-tests-beforeTransaction
  [this]
  (let [ tables (into-array ["products"]) ]
    (.deleteFromTables this tables)
    (.executeSqlScript this "file:db/load_data.sql" true)
  ))

(defn jdbc-product-dao-tests-testGetProductList
  [this]
  (let [productDao (springapp.bean/get-field this :productDao) products (.getProductList productDao) ]
    (org.junit.Assert/assertEquals "wrong number of products?" (java.lang.Integer. 3) (.size products))))

(defn jdbc-product-dao-tests-testSaveProduct
  [this]
  (let [productDao (springapp.bean/get-field this :productDao) products (.getProductList productDao)]
    (org.junit.Assert/assertEquals "wrong number of products?" (java.lang.Integer. 3) (.size products))
    (doall (map (fn [p] (.setPrice p 200.12) (.saveProduct productDao p) p) products))
    (let [updatedProducts (.getProductList productDao)]
      (org.junit.Assert/assertEquals "wrong number of products?" (java.lang.Integer. 3) (.size updatedProducts))
      ; notice doall
      (doall (map (fn [p] (org.junit.Assert/assertEquals "wrong price of product?" 200.12 (.getPrice p)) p) updatedProducts)))))

(gen-class
  :name springapp.repository.InMemoryProductDao
  :implements [springapp.repository.ProductDao]
  :main false
  :state state
  :init init
  :constructors {[java.util.List] [] }
  :prefix "in-memory-product-dao-")

(defn in-memory-product-dao-init
  [productList]
  [ [] (atom {:productList productList})])

(defn in-memory-product-dao-getProductList
  [this]
  (springapp.bean/get-field this :productList))

(defn in-memory-product-dao-saveProduct
  [this product]
  )
