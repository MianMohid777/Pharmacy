package Application.Model;

import java.util.HashMap;

public class Sales_Assistant extends Role{

    private Order order;
    public void processOrder(Boolean flag)
    {
        if(flag)
        {
            order.generateInvoice();
        }
        else
            order.cancel();
    }


}
