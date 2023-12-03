package Application.Model;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

public class CustomReport extends Report  implements SalesReport{

    public void assignData(HashMap<String, Vector<Object>> reportData)
    {
        super.setReportData(reportData);
    }
    @Override
    public void createSalesReport() {
        System.out.println("\t\t -----------Custom Sales Report--------");

        Set<String> categoryName = super.getReportData().keySet();

        Float total = 0.0F;
        float categTotal = 0.0F;

        for(String name : categoryName)
        {
            if(!Objects.equals(name, "TOTAL"))
            {
                Vector<Object> data = super.getReportData().get(name);

                System.out.println("Category Name: "+ name + "\n\n");

                for(int i = 0,j=1; i < data.size();i++)
                {
                    Product p = (Product) data.get(i);
                    int soldStock = (int) data.get(++i);

                    System.out.println("\t " + j++ + ") " + p.getName() + " Quantity Sold = " + soldStock);

                    categTotal+= p.getPrice()*soldStock;
                }

                System.out.println("\nCategory Total Sale: "+ categTotal + "\n\n");
                categTotal = 0.0F;
            }
            else {
                total = (Float) super.getReportData().get(name).get(0);

            }
        }

        System.out.println("\n\t Total Sales: " + total );
    }


    @Override
    public void display() {

    }
}
