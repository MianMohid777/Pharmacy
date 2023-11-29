package Presentation.Controller.Supporting;

import Application.Model.*;
import Application.Service.CategoryService;
import Application.Service.Implementation.CategoryS_I;
import Application.Service.Implementation.OrderS_I;
import Application.Service.Implementation.ProductS_I;
import Application.Service.OrderService;
import Application.Service.ProductService;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ManagerController {

    private ProductService productService;
    private CategoryService categoryService;

    private OrderService orderService;

    private HashMap<String,Product> productMap;
    private HashMap<String,String> productHierarchyMap;

    private HashMap<String,Category> categoryMap;

    private HashMap<String,String> parentChildCMap;  // Child <-> Parent //


    public ManagerController() throws SQLException {
        productService = new ProductS_I();
        categoryService = new CategoryS_I();
        orderService = new OrderS_I();

        List<Product> productList = productService.getAllProducts();
        List<Category> categoryList = categoryService.getAllCategories();

        productMap = new HashMap<>();
        categoryMap = new HashMap<>();
        parentChildCMap = new HashMap<>();

        for(Product p: productList)
        {
            productMap.put(p.getCode(),p);
        }
        parentChildCMap = categoryService.getParentChild();

        for(Category c : categoryList)
        {
            categoryMap.put(c.getName(),c);

            if(!parentChildCMap.containsKey(c.getName()) && !parentChildCMap.containsValue(c.getName()))
            {
                parentChildCMap.put(c.getName(),"NULL");
            }
        }


        productHierarchyMap = productService.findAllProductHierarchy();

        Set<String> keys = productHierarchyMap.keySet();

        for(String key : keys) {
            if (productMap.containsKey(key)) {
                Product p = productMap.get(key);
                p.setCategoryHierarchy(productHierarchyMap.get(key));
            }
        }

       // clearExpireStock();
    }


    public Boolean addProduct(String name,String desc, Integer qtyPerPack, Float price,String hierarchy, String categoryName) throws SQLException {
        Product p = new Product();
        p.setName(name.toUpperCase());
        p.setDesc(desc);
        p.setPrice(price);
        p.findCode();
        p.setStockQty(0);
        p.setPackQty(0);
        p.setCategoryHierarchy(hierarchy);

        p.setQtyPerPack(qtyPerPack);
        if(!productMap.containsKey(p.getCode()) && categoryMap.containsKey(categoryName))
        {
           productMap.put(p.getCode(),p);
           productService.add(p);

           linkProd_Category(categoryName,p);

           return true;
        }

        return false;
    }


    public Boolean updateProduct(String name,String code,String desc, Integer qtyPerPack, Float price) throws SQLException {
        Product p = productMap.get(code);

        p.setName(name.toUpperCase());
        p.setDesc(desc);
        p.setPrice(price);
        p.setStockQty(0);
        p.setPackQty(0);
        p.setQtyPerPack(qtyPerPack);

        if(productMap.containsKey(p.getCode()))
        {
            productMap.put(p.getCode(),p);
            productService.update(p);

            return true;
        }

        return false;
    }


    public Boolean removeProduct(String code) throws SQLException {

        if(productMap.containsKey(code))
        {
            productService.delete(code);
            productMap.remove(code);

            System.out.println("Product Deleted Successfully");

            return true;
        }

        return false;
    }

    public Boolean addStock(String code, Integer qty, LocalDate expiryDate) throws SQLException {

        if(productMap.containsKey(code) && qty > 0 && expiryDate.isAfter(LocalDate.now()))
        {
            productService.addStock(code,qty,expiryDate);

            Product p = productMap.get(code);
            p.setPackQty(p.getPackQty() + qty);
            int stockPlus = qty*p.getQtyPerPack();
            p.setStockQty(p.getStockQty() + stockPlus);
            productService.update(p);

            return true;
        }

        return false;
    }

    public Boolean removeStock(Integer id,String code, Integer qty) throws SQLException {

        if(productMap.containsKey(code)) {
            Product p = productMap.get(code);


            int newStockQty = p.getStockQty() - (p.getQtyPerPack()*qty);
            int packReduce = p.getPackQty() - qty;

            productService.deleteStock(id);

            p.setPackQty(packReduce);
            p.setStockQty(newStockQty);
            productService.update(p);

            return true;

        }
        return false;
    }

    public Boolean clearExpireStock() throws SQLException{

        HashMap<Integer,String> expireMap = productService.clearExpiredStock();

        if(!expireMap.isEmpty()) {

            Set<Integer> keys = expireMap.keySet();

            for(Integer s : keys)
            {
                Integer qty = productService.deleteStock(s);
                Product p = productMap.get(expireMap.get(s));

                int stockMinus = p.getQtyPerPack()*qty;
                p.setPackQty(p.getPackQty()-qty);
                p.setStockQty(p.getStockQty()-stockMinus);

                productService.update(p);

                System.out.println("Stock with Id " + s + " and Code " + expireMap.get(s) + " Removed");
            }
            return true;

        }
        return false;
    }


    public Boolean addCategory(String name,String desc) throws SQLException {
        Category c = new Category();
        c.setName(name);
        c.findCode();
        c.setDesc(desc);


        if(!categoryMap.containsKey(c.getName()))
        {
            categoryMap.put(c.getName(),c);
            categoryService.add(c);

            return true;
        }

        return false;
    }

    public Boolean updateCategory(String name,String desc,String code) throws SQLException {
        Category c = new Category();
        c.setName(name);
        c.setCode(code);
        c.setDesc(desc);


        if(categoryMap.containsKey(c.getName()))
        {
            categoryMap.put(c.getName(),c);
            categoryService.update(c);

            return true;
        }

        return false;
    }

    public Boolean updateHierarchy(String category,String newRegex) throws SQLException {
        List<String> code = productService.getProductsByCategory(category);

        for(String codes : code)
        {
            Product p = productMap.get(codes);
            String curr = p.getCategoryHierarchy();
            curr = curr.replaceAll(category,newRegex);
            p.setCategoryHierarchy(curr);

            productService.updateHierarchy(codes,curr);
        }
        return true;
    }


    public Boolean addSubCategory(String parent,String child) throws SQLException {


        if(categoryMap.containsKey(parent) && categoryMap.containsKey(child))
        {
            Category parentC = categoryMap.get(parent);
            Category childC = categoryMap.get(child);

            if(!parentC.getComponentsMap().containsKey(child))
            {
                if(parentChildCMap.containsKey(child) && !parentChildCMap.get(child).equals(parent)) {

                    parentC.add(childC);
                    parentChildCMap.put(child,parent);
                    categoryService.addSubCategory(parent, child);

                    return true;

                }
                else if(!parentChildCMap.containsKey(child))
                {
                    parentC.add(childC);
                    parentChildCMap.put(child,parent);
                    categoryService.addSubCategory(parent, child);

                    return true;
                }
            }


        }

        return false;
    }

    private void linkProd_Category(String parent,Product p)
    {
        if(categoryMap.containsKey(parent))
        {
            Category c = categoryMap.get(parent);
            c.add(p);
            System.out.println(p.getName() + " Added Under the Category of " + c.getName());

        }


    }


   public List<String> giveSearchResult(String search) throws SQLException {
        return productService.searchProduct(search);
    }

    public Product getSearchedProd(String name) throws SQLException {
        return productService.findByName(name);
    }

    public Product codeSearch(String code) throws SQLException {
       return productMap.get(code);
    }

    public String checkCodeProd(Product p)
    {
        return p.getCode();
    }

    public String checkCategoryName(String code) throws SQLException {

       return categoryService.findByCode(code).getName();
    }

    public Category categorySearchByCode(String code) throws SQLException {
        return categoryService.findByCode(code);
    }


    ///////////////////////-------REPORT----////////////////////////////////


    public void SalesReport(String type) throws SQLException {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        SalesReport report = null;

        if(type.equalsIgnoreCase("DAILY"))
        {
            start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            start = start.minusDays(1);
            report = new DailyReport();

        }
        else if(type.equalsIgnoreCase("WEEKLY"))
        {
            start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            DayOfWeek dayOfWeek = start.getDayOfWeek();
            int currDay = dayOfWeek.getValue();

            start = start.minusDays(currDay+1);

            report = new WeeklyReport();
        }
        else if(type.equalsIgnoreCase("MONTHLY"))
        {
            start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            int currDayM = start.getDayOfMonth();
            start = start.minusDays(currDayM+1);

            report = new MonthlyReport();
        }


        List<Order> orders = orderService.getOrderByDay(start,end);


        int first = orders.get(0).getId();
        int last = orders.get(orders.size()-1).getId();

        List<String> prodCodes = orderService.getProductsInPeriod(first,last);

        HashMap<String,Vector<Object>> categoryStats = new HashMap<>();

        for(String s : prodCodes)
        {
            Vector<Object> stats = new Vector<>();

            Product p = productMap.get(s);

            int qtySold = orderService.getSalesQty(first,last,s);

            String[] hierarchy = p.getCategoryHierarchy().split("/");
            String leafCategory = hierarchy[hierarchy.length-1];


            if(!categoryStats.containsKey(leafCategory))
            {
                stats.add(p);
                stats.add(qtySold);
                categoryStats.put(leafCategory,stats);

            }
            else
            {
                stats = categoryStats.get(leafCategory);
                stats.add(p);
                stats.add(qtySold);
            }

        }

        Float totalSales = orderService.getTotalByDate(start,end);

        Vector<Object> total = new Vector<>();
        total.add(totalSales);
        categoryStats.put("TOTAL",total);

       if(report != null)
       {
           report.assignData(categoryStats);

           report.createSalesReport();
       }
    }


    public HashMap<String, String> getParentChildCMap() {
        return parentChildCMap;
    }

    public HashMap<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(HashMap<String, Product> productMap) {
        this.productMap = productMap;
    }

    public HashMap<String, String> getProductHierarchyMap() {
        return productHierarchyMap;
    }

    public void setProductHierarchyMap(HashMap<String, String> productHierarchyMap) {
        this.productHierarchyMap = productHierarchyMap;
    }

    public HashMap<String, Category> getCategoryMap() {
        return categoryMap;
    }

    public void setCategoryMap(HashMap<String, Category> categoryMap) {
        this.categoryMap = categoryMap;
    }

    public void setParentChildCMap(HashMap<String, String> parentChildCMap) {
        this.parentChildCMap = parentChildCMap;
    }

    public List<Vector<Object>> pileStocks(String code) throws SQLException
    {
        return productService.getStocksOfProduct(code);
    }
    public static void main(String[] args) throws SQLException {
        ManagerController controller = new ManagerController();


        if(controller.clearExpireStock())
        {
            System.out.println("Stock Deleted Successfully");
        }

        else {

           /* if (controller.addProduct("Entox", "Testing", 10, 5F,"","Bo64368143"))
                System.out.println("Product Added Successfully");

            if (controller.addProduct("panadolcf", "Testing", 8, 8F,"","Ki-2047723204"))
                System.out.println("Product Added Successfully");

            if (controller.updateProduct("panadolcf","PA8Med1183019104", "Testing", 8, 9F,""))
                System.out.println("Product Updated Successfully");



            controller.addCategory("Diabetes", "Diabetic Medicines");
            controller.addCategory("Gastric","Stomach Medicines");
            controller.addCategory("Kidney","Excretory Medicines");
            controller.addCategory("Cardiac","Cardiac Medicines");
            controller.addCategory("Bones" ,"Supplements Medicines");

            controller.addSubCategory("Diabetes","Kidney");
            controller.addSubCategory("Cardiac","Kidney");
            controller.addSubCategory("Gastric","Bones");*/


           /* for(String s:controller.giveSearchResult("Panadolcf"))
            {
                System.out.println(s);

                Product p = controller.getSearchedProd(s);

                System.out.println(p.getName() + " has code " + p.getCode() + " with price " + p.getPrice() );

            }*/

            /*Product p = controller.codeSearch("EN5Med66129908");

            System.out.println(p.getName() + " has code " + p.getCode() + " with price " + p.getPrice() );

            System.out.println("Code for searched product is = "+ controller.checkCodeProd(p));

            Set<String> keys = controller.parentChildCMap.keySet();

            for(String s : keys)
            {
                String value = controller.parentChildCMap.get(s);
                System.out.println("Category: " + s + " has Parent Category = " + value);
            }
*/
            //controller.removeProduct("PPA20Med-79215811");

            //LocalDate date = LocalDate.of(2023,11,28);
            //controller.addStock("EN5Med66129908",20,date);

            //controller.SalesReport("monthly");

            System.out.println(controller.getSearchedProd("panadol"));
        }


    }
}
