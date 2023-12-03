package Application.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class InventoryReport extends Report{

    private HashMap<String,List<Vector<Object>>> stockProdMap;
    private Vector<Integer> stockQty;

    public void assignData(HashMap<String, List<Vector<Object>>> dataMap,Vector<Integer> stockQty)
    {
        stockProdMap = dataMap;
        this.stockQty = stockQty;
    }

    @Override
    public void display() {

        System.out.println("-----INVENTORY REPORT-----");




    }
}
