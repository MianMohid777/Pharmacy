package Application.Service;

import Application.Model.Product;
import Persistance.IDAO.DAO.ProductDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ProductService {

   void add(Product p) throws SQLException;

   List<Product> getAllProducts() throws SQLException;

   void update(Product p) throws SQLException;

   Product findByCode(String code) throws SQLException;

   Product findByName(String name) throws SQLException;

   List<String> searchProduct(String query) throws SQLException;



   void delete(String code) throws SQLException;

   void addStock(String code, int qty, LocalDate expiry) throws SQLException;

   void reduceStock(String code, int qty) throws SQLException;

   HashMap<Integer,String> clearExpiredStock() throws SQLException;

   Integer deleteStock(Integer id) throws SQLException;
   Integer findStockQty(Integer id, String code) throws SQLException;

   List<String> getProductsByCategory(String name) throws SQLException;;

   HashMap<String,String> findAllProductHierarchy() throws SQLException;

}
