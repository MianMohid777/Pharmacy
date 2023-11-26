package Application.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public interface SalesReport {

     void createSalesReport();

     void assignData(HashMap<String, Vector<Object>> reportData);
}
