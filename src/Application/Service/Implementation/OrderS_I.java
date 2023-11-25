package Application.Service.Implementation;

import Application.Model.Item;
import Application.Model.Order;
import Application.Model.Product;
import Application.Service.OrderService;
import Persistance.IDAO.DAO.OrderDAO;
import Persistance.IDAO.DAO.OrderProductDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class OrderS_I implements OrderService {


    private static OrderDAO orderRepo;

    private static OrderProductDAO orderProdRepo;


    public OrderS_I() throws SQLException {
        orderRepo = new OrderDAO();
        orderProdRepo = new OrderProductDAO();
    }

    @Override
    public void add(Order o) throws SQLException {

        orderRepo.save(o);
    }

    @Override
    public void delete(Integer id) throws SQLException {

        orderRepo.delete(id);
    }

    @Override
    public void update(Order o) throws SQLException {

        orderRepo.update(o);
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        return orderRepo.findAll();
    }

    @Override
    public Order findById(Integer id) throws SQLException {
        return orderRepo.findById(id);
    }

    @Override
    public void addOrderProdS(Order o) throws SQLException {

        Set<String> keys = o.getItemMap().keySet();

        for(String k : keys)
        {
            Product p = o.getItemMap().get(k).getP();
            Item i = o.getItemMap().get(k);

            orderProdRepo.save(o.getId(),p.getCode(),i.getQtyOrdered());
        }

    }

    @Override
    public HashMap<Integer, String> getAllProducts(Integer id) throws SQLException {
        return orderProdRepo.getAllProductsByOrderId(id);
    }
}
