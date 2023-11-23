package Application.Model;

import java.util.HashMap;
import java.util.Set;

public abstract class ItemContainer {

    protected int id;
    protected float totalPrice;

    private HashMap<String,Item> itemMap;



    public ItemContainer() {
        this.id = -1;
        this.itemMap = new HashMap<>();
        totalPrice = 0.0F;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashMap<String, Item> getItemMap() {
        return itemMap;
    }

    public void setItemMap(HashMap<String, Item> itemMap) {
        this.itemMap = itemMap;
    }

    public void add(Item i)
    {
        itemMap.put(i.getP().getName(),i);
    }

    public void remove(Item i)
    {
        itemMap.remove(i.getP().getName());
    }

    public void update(Item i, int qty)
    {
        itemMap.get(i.getP().getName()).setQtyOrdered(qty);
    }

    public float total() {

        Set<String> keys = itemMap.keySet();

        totalPrice = 0.0F;

        for(String s: keys)
        {
            Item i =itemMap.get(s);

            totalPrice += i.totalPrice();

        }

        return totalPrice;
    }


   abstract public void cancel();

}
