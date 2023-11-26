package Presentation.Controller.Supporting;

import Application.Model.*;
import Application.Service.Implementation.OrderS_I;
import Application.Service.Implementation.ProductS_I;
import Application.Service.OrderService;
import Application.Service.ProductService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AssistantController {

    private ProductService productService;
    private OrderService orderService;


    private static ItemContainer container;

    private HashMap<String,Product> productMap;
    public AssistantController() throws SQLException {
        productService = new ProductS_I();
        orderService = new OrderS_I();

        List<Product> productList = productService.getAllProducts();

        container = new Cart();
        productMap = new HashMap<>();

        for(Product p: productList)
        {
            productMap.put(p.getCode(),p);

        }
    }

    public Boolean addToCart(String code,Integer qty){ //Need to handle the Supply-Demand & Low Stock Check

        if(productMap.containsKey(code)) {
            Product p = productMap.get(code);

            if(p.getStockQty() > qty) {
                Item i = new Item();
                i.setP(p);
                i.setQtyOrdered(qty);

                container.add(i);

                System.out.println("Item Name = " + p.getName() + " with Ordered Qty = " + i.getQtyOrdered() + " Added to Cart");
                return true;
            }
            else
                System.out.println("Item Name = " + p.getName() + " with Ordered Qty = " + qty + " has Insufficient Stock");


        }
        return false;
    }

    public Boolean updateCart(String code, Integer qty)
    {
        if(productMap.containsKey(code)) {

            Product p = productMap.get(code);

            Item i = container.getItemMap().get(p.getName());
            i.setQtyOrdered(qty);

            System.out.println("Item Name = " + p.getName() + " with Ordered Qty = " + i.getQtyOrdered() + " Updated in Cart \n");
            return true;

        }
        return false;
    }

    public Boolean removeCart(String code)
    {
        if(productMap.containsKey(code))
        {
            Product p = productMap.get(code);

            Item i = container.getItemMap().get(p.getName());
            container.remove(i);

            System.out.println("Item Name = " + p.getName() + " with Ordered Qty = " + i.getQtyOrdered() + " Removed From Cart \n");
            return true;
        }

        return false;
    }

    public Boolean clearCart()
    {
        container.cancel();
        return true;
    }

    public Order makeOrder()
    {
        Order o = new Order();
        o.setItemMap(container.getItemMap());
        o.setTimeStamp(LocalDateTime.now());
        o.setName("Mohid Jillani");
        o.setCustomerName("Tester 2");

        return o;
    }

    public Boolean sellStock(String code, Integer qty) throws SQLException {

        if(productMap.containsKey(code))
        {
            Product p = productMap.get(code);

            if(p.getStockQty() > qty) // Supply > Demand
            {
                int newStockQty = p.getStockQty() - qty;
                int packReduce = newStockQty / p.getQtyPerPack();

                if(packReduce != p.getPackQty())
                {
                    int reducedNum = p.getPackQty() - packReduce;
                    productService.reduceStock(code,reducedNum);

                    System.out.println("Supplied " + qty + " Stock and Reduced the Packs by a Figure of " + reducedNum);

                }
                p.setPackQty(packReduce);
                p.setStockQty(newStockQty);
                productService.update(p);

                return true;
            }
        }
        return false;
    }

    List<String> giveSearchResult(String search) throws SQLException {
        return productService.searchProduct(search);
    }

    Product getSearchedProd(String name) throws SQLException {
        return productService.findByName(name);
    }

    Product codeSearch(String code) throws SQLException {
        return productMap.get(code);
    }

    String checkCodeProd(Product p)
    {
        return p.getCode();
    }

    public Boolean cancelOrder(Order o)
    {
        o.cancel();
        return true;
    }


    public List<Product> generateBill(Order o) throws SQLException {

        if(!o.getItemMap().isEmpty()) {
            Set<String> keys = o.getItemMap().keySet();

            List<Product> lowStock = new LinkedList<>();

            for (String s : keys) {
                Item i = o.getItemMap().get(s);
                Product p = i.getP();
                Integer qty = i.getQtyOrdered();

                sellStock(p.getCode(), qty);

                if(p.getStockQty() <= p.getQtyPerPack())
                {
                    lowStock.add(p);
                }

            }
            orderService.add(o);
            orderService.addOrderProdS(o);

            o.generateInvoice();

            return lowStock;
        }

        return null;
    }

    public static void main (String[] args) throws SQLException {
        AssistantController controller = new AssistantController();

        controller.addToCart("TE35Med2571410", 5);
        controller.addToCart("EN5Med66129908", 10);

        //controller.updateCart("PA8Med1183019104", 10);

        //controller.removeCart("PA20Med-79215811");

        Order currOrder = controller.makeOrder();

        controller.generateBill(currOrder);

        //controller.sellStock("TE35Med2571410",60);

    }
}
