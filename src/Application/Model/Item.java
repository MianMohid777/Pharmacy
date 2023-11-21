package Application.Model;

public class Item {

    private int qtyOrdered;


    private Product p;

    public Item(int qtyOrdered, Product p) {
        this.qtyOrdered = qtyOrdered;

        this.p = p;
    }

    public int getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(int qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public Product getP() {
        return p;
    }

    public float totalPrice()
    {
        return qtyOrdered*p.getPrice();
    }
}

