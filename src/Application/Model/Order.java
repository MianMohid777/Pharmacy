package Application.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class Order extends ItemContainer{
   private String customerName;
   private LocalDateTime timeStamp;

    public Order(String customerName, LocalDateTime timeStamp) {
        super();
        this.customerName = customerName;
        this.timeStamp = timeStamp;
    }


    public Order() {
        super();
        this.customerName = "";
        this.timeStamp = null;
    }

    public void cancel()
    {
        this.getItemMap().clear();
        customerName = "";
        timeStamp = null;
    }

    public void generateInvoice()
    {
        Set<String> keys = super.getItemMap().keySet();

        float totalPrice = 0.0F;

        System.out.println("Item \t" + "Qty \t" + "Price \t" + "Total Price " );
        for(String s: keys)
        {
            Item i = super.getItemMap().get(s);

            System.out.println(s + "\t" + i.getQtyOrdered() + "\t" + i.getP().getPrice() + "\t" + i.totalPrice());
            totalPrice += i.totalPrice();
        }

        System.out.println(" \t\t Grand Total : \t" +totalPrice);
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
}
