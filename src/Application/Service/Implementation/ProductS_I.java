package Application.Service.Implementation;

import Application.Model.Product;
import Application.Service.ProductService;
import Persistance.IDAO.DAO.ProductCategoryDAO;
import Persistance.IDAO.DAO.ProductDAO;
import Persistance.IDAO.DAO.ProductStockDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductS_I implements ProductService {

    private static ProductDAO productRepo;
    private static ProductCategoryDAO productCategoryRepo;

    private static ProductStockDAO productStockRepo;

    public ProductS_I() throws SQLException {

        productRepo = new ProductDAO();
        productCategoryRepo = new ProductCategoryDAO();
        productStockRepo = new ProductStockDAO();

    }

    @Override
    public void add(Product p) throws SQLException {

        productRepo.save(p);
        productCategoryRepo.save(p.getCode(),p.getCategoryHierarchy());

    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        return productRepo.findAll();
    }

    @Override
    public void update(Product p) throws SQLException {
        productRepo.update(p);

    }

    @Override
    public Product findByCode(String code) throws SQLException {
        return productRepo.findById(code);
    }

    @Override
    public Product findByName(String name) throws SQLException {
        return productRepo.findByName(name);
    }

    @Override
    public List<String> searchProduct(String query) throws SQLException {
        return productRepo.findBySearch(query);
    }


    @Override
    public void delete(String code) throws SQLException {

        productRepo.delete(code);
    }

    @Override
    public void addStock(String code, int qty, LocalDate expiry) throws SQLException {

        productStockRepo.save(code,qty,expiry);
    }

    @Override
    public void reduceStock(String code, int qty) throws SQLException {

        productStockRepo.update(code,qty);

    }

    @Override
    public HashMap<Integer,String> clearExpiredStock() throws SQLException {

        List<Integer> stocksId = productStockRepo.checkExpiry();

        if(!stocksId.isEmpty())
        {
            HashMap<Integer,String> expiredStockMap = productStockRepo.getExpiredProduct(stocksId);

            if(!expiredStockMap.isEmpty())
                return expiredStockMap;
        }
        return new HashMap<>();
    }

    @Override
    public Integer deleteStock(Integer id) throws SQLException {
        return productStockRepo.delete(id);
    }

    @Override
    public Integer findStockQty(Integer id, String code) throws SQLException {
        return productStockRepo.findStock(id,code);
    }

    @Override
    public List<String> getProductsByCategory(String name) throws SQLException {
        return productCategoryRepo.findProductInCategory(name);
    }

    @Override
    public HashMap<String, String> findAllProductHierarchy() throws SQLException {
        return productCategoryRepo.findAllProductCategories();
    }
}
