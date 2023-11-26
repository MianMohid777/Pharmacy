package Presentation.Controller;

import Application.Model.Category;
import Application.Model.Product;
import Application.Service.CategoryService;
import Application.Service.Implementation.CategoryS_I;
import Application.Service.Implementation.ProductS_I;
import Application.Service.ProductService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ManagerController {

    private ProductService productService;
    private CategoryService categoryService;

    private HashMap<String,Product> productMap;

    private HashMap<String,Category> categoryMap;

    private HashMap<String,String> parentChildCMap;  // Child <-> Parent //


    public ManagerController() throws SQLException {
        productService = new ProductS_I();
        categoryService = new CategoryS_I();

        List<Product> productList = productService.getAllProducts();
        List<Category> categoryList = categoryService.getAllCategories();

        productMap = new HashMap<>();
        categoryMap = new HashMap<>();
        parentChildCMap = new HashMap<>();

        for(Product p: productList)
        {
            productMap.put(p.findCode(),p);
        }

        for(Category c : categoryList)
        {
            categoryMap.put(c.getName(),c);
        }

        parentChildCMap = categoryService.getParentChild();
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


    public Boolean updateProduct(String name,String code,String desc, Integer qtyPerPack, Float price,String hierarchy) throws SQLException {
        Product p = productMap.get(code);

        p.setName(name.toUpperCase());
        p.setDesc(desc);
        p.setPrice(price);
        p.setStockQty(0);
        p.setPackQty(0);
        p.setCategoryHierarchy(hierarchy);
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

    public Boolean clearExpireStock() throws SQLException {

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

    String checkCategoryName(String code) throws SQLException {

       return categoryService.findByCode(code).getName();
    }

    Category categorySearchByCode(String code) throws SQLException {
        return categoryService.findByCode(code);
    }

    public static void main(String[] args) throws SQLException {
        ManagerController controller = new ManagerController();


        if(controller.clearExpireStock())
        {
            System.out.println("Stock Deleted Successfully");
        }

        else {

            if (controller.addProduct("Entox", "Testing", 10, 5F,"","Bo64368143"))
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
            controller.addSubCategory("Gastric","Bones");


            for(String s:controller.giveSearchResult("Panadolcf"))
            {
                System.out.println(s);

                Product p = controller.getSearchedProd(s);

                System.out.println(p.getName() + " has code " + p.getCode() + " with price " + p.getPrice() );

            }

            Product p = controller.codeSearch("EN5Med66129908");

            System.out.println(p.getName() + " has code " + p.getCode() + " with price " + p.getPrice() );

            System.out.println("Code for searched product is = "+ controller.checkCodeProd(p));

            Set<String> keys = controller.parentChildCMap.keySet();

            for(String s : keys)
            {
                String value = controller.parentChildCMap.get(s);
                System.out.println("Category: " + s + " has Parent Category = " + value);
            }

            controller.removeProduct("PPA20Med-79215811");
        }

    }
}
