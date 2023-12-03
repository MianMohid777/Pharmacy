package Application.Model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public interface SalesReport {

     void createSalesReport() throws IOException;

     void assignData(HashMap<String, Vector<Object>> reportData);

     void assignDate(LocalDate start,LocalDate end);
}
