package Application.Model;

import java.util.Date;

public class Product implements Component{


    private String code;
    private String name;
    private String desc;
    private int stockQty;

    private int packQty;

    private int qtyPerPack;

    private float price;

    private Date expiry;

    private String categoryHierarchy;

    public Product(String code, String name, String desc, int stockQty, int packQty, int qtyPerPack, float price, Date expiry,String categoryHierarchy) {
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.stockQty = stockQty;
        this.packQty = packQty;
        this.qtyPerPack = qtyPerPack;
        this.price = price;
        this.expiry = expiry;
        this.categoryHierarchy = categoryHierarchy;
    }

    public Product() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getPackQty() {
        return packQty;
    }

    public void setPackQty(int packQty) {
        this.packQty = packQty;
    }

    public int getQtyPerPack() {
        return qtyPerPack;
    }

    public void setQtyPerPack(int qtyPerPack) {
        this.qtyPerPack = qtyPerPack;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public String getCategoryHierarchy() {
        return categoryHierarchy;
    }

    public void setCategoryHierarchy(String categoryHierarchy) {
        this.categoryHierarchy = categoryHierarchy;
    }

    @Override
    public void display() {

        System.out.println("Product Name: " + name + "\n" + "Product Code: " + code + "\n" + "Product Price: " + price
        + "\n" + "Product Avail Stock: " + stockQty);
    }

    @Override
    public String findCode() {
        return String.valueOf(this.name.hashCode());
    }
}
