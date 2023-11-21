package Application.Model;

import java.util.Set;

public class Cart extends ItemContainer{

    @Override
    public void cancel() {
        super.getItemMap().clear();
    }

    public Order generateOrder()
    {
        Set<String> keys = super.getItemMap().keySet();

        Order order = new Order();

        for(String s: keys)
        {
            Item i = super.getItemMap().get(s);

            order.add(i);
        }

        return order;
    }
}
