package Application.Service;

import Application.Model.Order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface OrderService {

    void add(Order o) throws SQLException;

    void delete(Integer id) throws SQLException;

    void update(Order o) throws SQLException;

    List<Order> getAllOrders() throws SQLException;

    Order findById(Integer id) throws SQLException;

    void addOrderProdS(Order o) throws SQLException;

    HashMap<Integer, String> getAllProducts(Integer id) throws SQLException;

}
