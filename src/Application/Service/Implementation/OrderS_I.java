package Application.Service.Implementation;

import Application.Model.Item;
import Application.Model.Order;
import Application.Model.Product;
import Application.Service.OrderService;
import Persistance.IDAO.DAO.OrderDAO;
import Persistance.IDAO.DAO.OrderProductDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        for (String k : keys) {
            Product p = o.getItemMap().get(k).getP();
            Item i = o.getItemMap().get(k);

            orderProdRepo.save(o.getId(), p.getCode(), i.getQtyOrdered());
        }

    }

    @Override
    public List<String>  getAllProducts(Integer id) throws SQLException {
        return orderProdRepo.getAllProductsByOrderId(id);
    }

    public List<String>  getOrderProducts(Integer id) throws SQLException {
        return orderProdRepo.getAllProductsByOrderId(id);
    }
    public List<Order> getOrderByDay(LocalDateTime startDate,LocalDateTime endDat) throws SQLException {
        return orderRepo.findByDate( startDate,endDat);
    }

    public Integer getSalesQty(Integer start,Integer end, String name) throws SQLException {
        return orderProdRepo.salesQty(start,end,name);
    }

    public List<String> getProductsInPeriod(Integer start, Integer end) throws SQLException
    {
        return orderProdRepo.getProductsInPeriod(start,end);
    }

    public Float getTotalByDate(LocalDateTime startDate,LocalDateTime endDate) throws SQLException
    {
        return orderRepo.findByTotalByDate(startDate, endDate);
    }

    public static void main(String[] args) throws SQLException {
        OrderS_I test = new OrderS_I();

        LocalDateTime start = LocalDateTime.now();


        start = start.withHour(0).withMinute(0).withSecond(0).withNano(0);


        for(Order order: test.getOrderByDay(start,LocalDateTime.now()))
        {
            System.out.println(order.getId());
        }

         List<Order> list =  test.getOrderByDay(start,LocalDateTime.now());

        System.out.println(test.getSalesQty(list.get(0).getId(),list.get(list.size()-1).getId(),"TE35Med2571410"));
        System.out.println(test.getTotalByDate(start,LocalDateTime.now()));
    }
}
