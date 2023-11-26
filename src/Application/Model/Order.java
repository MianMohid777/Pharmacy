package Application.Model;

import java.time.LocalDateTime;
import java.util.Set;

public class Order extends ItemContainer{
   private String customerName;

   private String name;

   private LocalDateTime timeStamp;

   private float totalPrice;



    public Order(String customerName, String name, LocalDateTime timeStamp, float totalPrice) {
        super();
        this.customerName = customerName;
        this.name = name;
        this.timeStamp = timeStamp;
        this.totalPrice = totalPrice;
    }

    public Order() {
        super();
        this.customerName = "";
        this.timeStamp = null;
        totalPrice = 0.0F;
    }

    public void cancel()
    {
        this.getItemMap().clear();
        customerName = "";
        timeStamp = null;
        totalPrice = 0.0F;
    }

    public void generateInvoice()
    {
        Set<String> keys = super.getItemMap().keySet();

        totalPrice = super.total();

        System.out.println("Receipt Id: " + id );
        System.out.println("User: " + name );
        System.out.println("Customer Name: " + customerName );
        System.out.println("Order DateTime: " + timeStamp );

        System.out.println("\n Item     \t" + "Qty     \t" + "Price     \t" + "Total Price  " );
        for(String s: keys)
        {
            Item i = super.getItemMap().get(s);

            System.out.println(s + " \t     " + i.getQtyOrdered() + "     \t" + i.getP().getPrice() + "     \t" + i.totalPrice());
        }

        System.out.println(" \n\t\t   Grand Total : \t" +totalPrice);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public float getTotalPriceFromContainer()
    {
        return super.total();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
