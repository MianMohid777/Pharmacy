package Application.Service;

import Application.Model.Order;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface OrderService {

    void add(Order o) throws SQLException;

    void delete(Integer id) throws SQLException;

    void update(Order o) throws SQLException;

    List<Order> getAllOrders() throws SQLException;

    Order findById(Integer id) throws SQLException;

    void addOrderProdS(Order o) throws SQLException;

    List<String>  getAllProducts(Integer id) throws SQLException;

    List<String>  getOrderProducts(Integer id) throws SQLException;

     List<Order> getOrderByDay(LocalDateTime startDate,LocalDateTime endDat) throws SQLException;

     Integer getSalesQty(Integer start,Integer end, String name) throws SQLException;

     List<String> getProductsInPeriod(Integer start, Integer end) throws SQLException;

     Float getTotalByDate(LocalDateTime startDate,LocalDateTime endDate) throws SQLException;

}
